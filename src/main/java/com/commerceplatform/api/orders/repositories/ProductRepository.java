package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
