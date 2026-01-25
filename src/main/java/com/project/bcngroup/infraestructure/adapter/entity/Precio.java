package com.project.bcngroup.infraestructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Precio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRICE_ID")
    private Long id;

    @Column(name = "BRAND_ID")
    private int brandId;

    @Column(name = "START_DATE ")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private int priceList;

    @Column(name = "PRODUCT_ID")
    private int productoId;

    @Column(name = "PRIORITY")
    private int priority;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "CURR")
    private String curr;
}
