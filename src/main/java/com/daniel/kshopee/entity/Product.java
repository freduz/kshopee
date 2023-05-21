package com.daniel.kshopee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_tbl")
public class Product {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "product_name")
    private String name;
    @Column(name = "product_description")
    private String description;

    @Column(name = "product_qty")
    private int quantity;

    @Column(name = "product_price")
    private double price;

}
