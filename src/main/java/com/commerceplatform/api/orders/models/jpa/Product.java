package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//Defini como parcial, pois sao abstracoes de uma tabela de um servico externo
// WIP - Se o servico de produtos estiver fora do ar, eu devo garantir que no minimo a tabela parcial
// forneca dados temporários (Isso ta no principio de event carried state transfer)
@Table(name = "partial_product")
@Builder
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private Long externalId;
}
