package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
@Builder
public class OrderDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

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
