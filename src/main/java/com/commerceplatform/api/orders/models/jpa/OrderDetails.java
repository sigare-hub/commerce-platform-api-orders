package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "order_details")
@Builder
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderModel orderModel;

    @Column(name = "address_id")
    private Long addressId;

    private Double subtotal;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    private Double discount;

    private Double total;

    @Column(name = "paid_by")
    private String paidBy; // credit card / pix / etc...

    public OrderDetails() {
    }

    public OrderDetails(Long id, OrderModel orderModel, Long addressId, Double subtotal, Double shippingFee, Double discount, Double total, String paidBy) {
        this.id = id;
        this.orderModel = orderModel;
        this.addressId = addressId;
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.discount = discount;
        this.total = total;
        this.paidBy = paidBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }
}
