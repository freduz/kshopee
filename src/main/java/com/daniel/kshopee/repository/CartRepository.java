package com.daniel.kshopee.repository;

import com.daniel.kshopee.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
