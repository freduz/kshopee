package com.daniel.kshopee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_tbl")
@Builder
public class Product {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name",nullable = false)
    private String name;
    @Column(name = "product_description",nullable = false)
    private String description;

    @Column(name = "product_qty",nullable = false)
    private int quantity;

    @Column(name = "product_price",nullable = false)
    private double price;

}
