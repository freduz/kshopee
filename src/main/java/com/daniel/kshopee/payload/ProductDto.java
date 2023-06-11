package com.daniel.kshopee.payload;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDto {


    private long id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private List<String> images;


    private String sellerName;
}
