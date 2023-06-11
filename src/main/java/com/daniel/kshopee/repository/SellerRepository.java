package com.daniel.kshopee.repository;

import com.daniel.kshopee.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    Optional<Seller> findByUserId(long id);
}
