package com.daniel.kshopee.controller;


import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.payload.CartItemDto;
import com.daniel.kshopee.service.CartService;
import com.daniel.kshopee.service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/cart")
public class CartController {

    private static final org.slf4j.Logger logger =  LoggerFactory.getLogger(CartController.class);
    private static CartService cartService;
    private static ProductService productService;


    public CartController(CartService cartService,ProductService productService){
        this.cartService = cartService;
        this.productService = productService;
    }


    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartItemDto cartItemDto){
        Cart cart = cartService.addToCart(cartItemDto);
    return new ResponseEntity<>(cart,HttpStatus.CREATED);
    }

}
