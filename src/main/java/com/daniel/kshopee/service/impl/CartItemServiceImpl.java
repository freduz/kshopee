package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.CartItem;
import com.daniel.kshopee.payload.CartItemDto;
import com.daniel.kshopee.repository.CartItemRepository;
import com.daniel.kshopee.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }
    @Override
    public CartItem addCartItem(CartItemDto cartItemDto) {
        return null;
    }
}
