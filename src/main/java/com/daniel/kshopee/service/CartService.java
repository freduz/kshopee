package com.daniel.kshopee.service;

import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.payload.CartItemDto;

public interface CartService {

    Cart addToCart(CartItemDto cartItemDto);
}
