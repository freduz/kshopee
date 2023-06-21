package com.daniel.kshopee.repository;

import com.daniel.kshopee.entity.Cart;
import com.daniel.kshopee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(User user);

}
