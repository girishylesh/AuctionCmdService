package com.eauction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eauction.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> findByNameAndAuctionUserUserId(String name, Long userId);
}