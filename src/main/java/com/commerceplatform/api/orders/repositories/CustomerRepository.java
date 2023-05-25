package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
