package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByExternalId(Long id);
}
