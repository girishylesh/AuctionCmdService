package com.eauction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eauction.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> findByNameAndAuctionUserUid(String name, String uid);
	public Optional<Product> findByUid(String uid);
	public void deleteByUid(String uid);
}
