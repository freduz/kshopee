package com.daniel.kshopee.controller;


import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/cart")
public class CartController {

    private static final org.slf4j.Logger logger =  LoggerFactory.getLogger(CartController.class);

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(){
    return new ResponseEntity<>("Product added",HttpStatus.CREATED);
    }

}
