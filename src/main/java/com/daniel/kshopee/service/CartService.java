package com.daniel.kshopee.service;

import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.payload.CartItemDto;
import com.daniel.kshopee.payload.CartResponse;

import java.util.List;

public interface CartService {

    Cart addToCart(CartItemDto cartItemDto);
    String removeFromCart(long productId);

    List<CartResponse> getCartItems() throws ClassNotFoundException;

    List<CartResponse> updateQuantity(CartItemDto cartItemDto);
}
