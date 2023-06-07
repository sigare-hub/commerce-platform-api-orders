package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.exceptions.BadRequestException;
import com.commerceplatform.api.orders.exceptions.NotFoundException;
import com.commerceplatform.api.orders.exceptions.ValidationException;
import com.commerceplatform.api.orders.integrations.api.connections.ApiProductsIntegration;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.models.jpa.Product;
import com.commerceplatform.api.orders.repositories.CustomerRepository;
import com.commerceplatform.api.orders.repositories.OrderItemRepository;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import com.commerceplatform.api.orders.repositories.ProductRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final ApiProductsIntegration apiProductsIntegration;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @PersistenceContext
    private EntityManager em;

    public OrderService(ApiProductsIntegration apiProductsIntegration, CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository,
                        OrderItemRepository orderItemRepository) {
        this.apiProductsIntegration = apiProductsIntegration;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // secured route -> necessary bearer token
    @Transactional
    public OrderModel createOrder(OrderDto input) {
        var inputCustomerId = input.getCustomerId();
        var inputOrderItemsDto = input.getOrderItems();

        // verificar se exite o customerId
        if(Objects.nonNull(inputCustomerId)) {
            var existCustomer = customerRepository.findById(inputCustomerId);
            if(existCustomer.isEmpty()) {
                // apiCustomerIntegration para verificar se o usuario eh válido
                var customer = new Customer();
                customer.setId(input.getCustomerId());
                customerRepository.save(customer);
            }
        }

        // verificar se existe items no meu pedido
        if(inputOrderItemsDto.isEmpty()) {
            throw new BadRequestException("You cannot create an order without at least informing an item");
        }

        // validar os items no meu pedido na api de produtos
        List<OrderItemDto> validatedOrderItems = validateOrderItems(inputOrderItemsDto);
        ArrayList<OrderItem> mapperOrderItems = getOrderItems(validatedOrderItems);

        var order = new OrderModel();
        var total = mapperOrderItems.stream()
                .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum();

        order.setTotal(total);
        order.setStatus(OrderStatus.PROCESSING);

        var createdOrder = orderRepository.save(order);

        for(OrderItem orderItem : mapperOrderItems) {
            orderItem.setOrder(createdOrder);
            orderItemRepository.save(orderItem);
        }

        return createdOrder;
    }

    private ArrayList<OrderItem> getOrderItems(List<OrderItemDto> validatedOrderItems) {
        // converter os items DTO em item comum
        var mapperOrderItems = new ArrayList<OrderItem>();

        for(OrderItemDto validatedItem : validatedOrderItems) {
            /*
            * Se o produto que eu ja validei, existe na api de produtos,  mas nao existe localmente
            * eu salvo ele localmente, caso contrario, eu so pego ele que ja existe e atribuo
            * */
            Optional<Product> existingProductOptional = productRepository.findByExternalId(validatedItem.getProductId());

            var orderItem = new OrderItem();

            if(existingProductOptional.isPresent()) {
                orderItem.setProduct(existingProductOptional.get());
            } else {
                var product = new Product();
                product.setExternalId(validatedItem.getProductId());

                var createdProduct = productRepository.save(product);
                orderItem.setProduct(createdProduct);
            }
            orderItem.setQuantity(validatedItem.getQuantity());
            orderItem.setPrice(validatedItem.getPrice());
            mapperOrderItems.add(orderItem);
        }
        return mapperOrderItems;
    }

    private List<OrderItemDto> validateOrderItems(List<OrderItemDto> orderItems) {
        // Pegar somente os ids de cada item no pedido
        List<Long> productIds = orderItems.stream()
                .map(OrderItemDto::getProductId).toList();

        // Verificar na API de produtos se os produtos existem
        List<ProductDto> products = apiProductsIntegration.getProductsByIds(productIds);
        var validOrderItems = new ArrayList<OrderItemDto>();
        var invalidOrderItems = new ArrayList<OrderItemDto>();

        /// Validar se o produto existe e salvar na lista de itens válidos
        for (OrderItemDto orderItem : orderItems) {
            Long orderItemId = orderItem.getProductId();
            boolean productExists = products
                    .stream()
                    .anyMatch(product -> product.getId().equals(orderItemId));

            if (productExists) {
                validOrderItems.add(orderItem);
            } else {
                invalidOrderItems.add(orderItem);
            }
        }
        if(!invalidOrderItems.isEmpty()) {
            var invalidResponse = new HashMap<String, List<String>>();
            var invalidIds = new ArrayList<String>();

            for (OrderItemDto item :  validOrderItems) {
                invalidIds.add(item.getProductId().toString());
            }
            invalidResponse.put("invalid_products_ids", invalidIds);
            throw new ValidationException(invalidResponse);
        }
        return validOrderItems;
    }

    public List<OrderModel> findAll() {
        try {
            EntityGraph<OrderModel> graph = em.createEntityGraph(OrderModel.class);
            graph.addAttributeNodes("orderItems");

            // Cria uma consulta com o EntityGraph
            TypedQuery<OrderModel> query = em.createQuery("SELECT o FROM OrderModel o", OrderModel.class);
            query.setHint("javax.persistence.fetchgraph", graph);

            return query.getResultList();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public OrderModel findById(Long id) {
                // Cria um EntityGraph para buscar o pedido com os itens de pedido
                EntityGraph<OrderModel> graph = em.createEntityGraph(OrderModel.class);
        graph.addAttributeNodes("orderItems");

        // Cria uma consulta com o EntityGraph
        TypedQuery<OrderModel> query = em.createQuery("SELECT o FROM OrderModel o WHERE o.id = :id", OrderModel.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", graph);

        return query.getSingleResult();
    }
}
