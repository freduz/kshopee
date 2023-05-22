package com.daniel.kshopee.repository;

import com.daniel.kshopee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
