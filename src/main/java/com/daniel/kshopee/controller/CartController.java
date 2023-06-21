package com.daniel.kshopee.controller;


import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.payload.CartItemDto;
import com.daniel.kshopee.payload.CartResponse;
import com.daniel.kshopee.service.CartService;
import com.daniel.kshopee.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/cart")
public class CartController {

    private static final Logger logger =  LoggerFactory.getLogger(CartController.class);
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

    @GetMapping
    public List<CartResponse> getCart() throws ClassNotFoundException {
        List<CartResponse> cartResponse = cartService.getCartItems();
        return cartResponse;
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam("productId") long productId){
        String status = cartService.removeFromCart(productId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<List<CartResponse>> updateCart(@RequestBody CartItemDto cartItemDto){
        List<CartResponse> responses = cartService.updateQuantity(cartItemDto);
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

}
