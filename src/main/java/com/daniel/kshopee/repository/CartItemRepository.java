package com.daniel.kshopee.repository;

import com.daniel.kshopee.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
