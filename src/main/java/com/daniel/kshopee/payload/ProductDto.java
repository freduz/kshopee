package com.daniel.kshopee.payload;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {


    private long id;
    private String name;
    private String description;
    private int quantity;
    private double price;
}
