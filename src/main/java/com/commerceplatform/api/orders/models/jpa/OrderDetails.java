package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
