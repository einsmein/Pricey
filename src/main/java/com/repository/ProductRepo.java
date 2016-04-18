package com.repository;

import com.domain.Product;
import com.domain.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
