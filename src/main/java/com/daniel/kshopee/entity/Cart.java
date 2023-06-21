package com.daniel.kshopee.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Entity
@Table(name = "cart_tbl")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    public CartItem getCartItemByProductId(long productId) {
        Predicate<CartItem> filterCart = cartItem -> {
            return cartItem.getProduct().getId().equals(productId);
        };
        CartItem cartItem = null;
        if(this.cartItems !=null){
            cartItem = this.cartItems.stream().filter(filterCart).findFirst().orElse(null);
        }

        return cartItem;
    }

    public void addCartItem(CartItem cartItem){
        this.cartItems.add(cartItem);
    }

    public void removedCartItem(CartItem cartItem){
        cartItems.remove(cartItem);
    }
}
