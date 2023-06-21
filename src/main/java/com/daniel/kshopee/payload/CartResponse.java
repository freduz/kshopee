package com.daniel.kshopee.payload;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private long productId;
    private String productName;
    private int quantity;
    private double price;
    private String image;

}
