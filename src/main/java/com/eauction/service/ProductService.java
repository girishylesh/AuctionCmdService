package com.eauction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauction.entity.Product;
import com.eauction.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Optional<Product> getProductById(Long productId) {
		return productRepository.findById(productId);
	}
	
	public Optional<Product> getProductByNameAndUser(String name, Long userId) {
		return productRepository.findByNameAndAuctionUserUserId(name, userId);
	}
}
