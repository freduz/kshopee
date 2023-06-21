package com.daniel.kshopee.service.impl;

import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.entity.CartItem;
import com.daniel.kshopee.entity.Product;
import com.daniel.kshopee.entity.User;
import com.daniel.kshopee.payload.CartItemDto;
import com.daniel.kshopee.payload.CartResponse;
import com.daniel.kshopee.repository.CartItemRepository;
import com.daniel.kshopee.repository.CartRepository;
import com.daniel.kshopee.repository.ProductRepository;
import com.daniel.kshopee.repository.UserRepository;
import com.daniel.kshopee.service.CartService;
import com.daniel.kshopee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private UserService userService;
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           UserService userService,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository
    ){
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart addToCart(CartItemDto cartItemDto) {
        Product product = this.productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userService.getCurrentLoggedUser();
        Cart cart = this.cartRepository.findByUser(user).orElse(null);
        if(cart == null){
            cart = Cart.builder()
                    .user(user)
                    .build();
//
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

    @Override
    public String removeFromCart(long productId) {
       User user = userService.getCurrentLoggedUser();
       Cart cart = cartRepository.findByUser(user).orElse(null);
       CartItem cartItem = cart.getCartItemByProductId(productId);
       if(cartItem != null){
           cart.removedCartItem(cartItem);
           cartRepository.save(cart);
           cartItemRepository.delete(cartItem);
           return "Product removed from the cart";
       }else{
           return "The product is not belonging with this cart";
       }
    }

    @Override
    public List<CartResponse> getCartItems() {
        Function<CartItem, CartResponse> mapToResponse = getCartItemCartResponseFunction();
        User user = this.userService.getCurrentLoggedUser();
        Cart cart = cartRepository.findByUser(user).orElse(null);
        List<CartResponse> responseList =  cart.getCartItems().stream().map(mapToResponse).collect(Collectors.toList());
        return responseList;
    }

    private static Function<CartItem, CartResponse> getCartItemCartResponseFunction() {
        Function<CartItem,CartResponse> mapToResponse = cartItem -> {
           return CartResponse.builder()
                   .price(cartItem.getProduct().getPrice())
                   .productName(cartItem.getProduct().getName())
                   .productId(cartItem.getProduct().getId())
                   .quantity(cartItem.getQuantity())
                   .image(cartItem.getProduct().getProductMedia().get(0).getUrl())
                   .build();

        };
        return mapToResponse;
    }

    @Override
    public List<CartResponse> updateQuantity(CartItemDto cartItemDto) {
        User user = userService.getCurrentLoggedUser();
        Cart cart = cartRepository.findByUser(user).orElse(null);
        Function<CartItem, CartResponse> mapToResponse = getCartItemCartResponseFunction();
        CartItem cartItem = cart.getCartItemByProductId(cartItemDto.getProductId());
        if(cartItem !=null){
            int currentQuantity = cartItem.getQuantity();
            int newQuantity = currentQuantity + cartItemDto.getQuantity();
            if(newQuantity >=0){
                cartItem.setQuantity(newQuantity);
                cartRepository.save(cart);
            }else{
                throw new RuntimeException("Invalid quantity provided");
            }
        }else{
            throw new RuntimeException("Invalid product ID");
        }

        List<CartResponse> responseList =  cart.getCartItems().stream().map(mapToResponse).collect(Collectors.toList());
        return responseList;

    }
}
