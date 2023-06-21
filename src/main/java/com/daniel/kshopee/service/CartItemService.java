package com.daniel.kshopee.service;

import com.daniel.kshopee.entity.CartItem;
import com.daniel.kshopee.payload.CartItemDto;

public interface CartItemService {

    CartItem addCartItem(CartItemDto cartItemDto);

}
