package com.daniel.kshopee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products_tbl")
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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    @JsonBackReference
    private User user;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<ProductMedia> productMedia;



}
