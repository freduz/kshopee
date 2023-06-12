package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.entity.CartItem;
import com.daniel.kshopee.entity.Product;
import com.daniel.kshopee.entity.User;
import com.daniel.kshopee.payload.CartItemDto;
import com.daniel.kshopee.repository.CartRepository;
import com.daniel.kshopee.repository.ProductRepository;
import com.daniel.kshopee.repository.UserRepository;
import com.daniel.kshopee.service.CartItemService;
import com.daniel.kshopee.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private CartItemService cartItemService;
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartItemService cartItemService,
                           CartRepository cartRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository){
        this.cartItemService = cartItemService;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(CartItemDto cartItemDto) {
        Product product = this.productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        String email  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No user found"));
        Cart cart = this.cartRepository.findByUser(user).orElse(null);
        if(cart == null){
            cart = Cart.builder()
                    .user(user)
                    .build();
        }
        CartItem existingCartItem = cart.getCartItemByProductId(cartItemDto.getProductId());
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItemDto.getQuantity());
        }else{
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .quantity(cartItemDto.getQuantity())
                    .product(product)
                    .build();
            cart.addCartItem(cartItem);
        }

        Cart savedCartItem = cartRepository.save(cart);


        return savedCartItem;
    }
}
