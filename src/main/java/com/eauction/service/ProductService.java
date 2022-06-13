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
	
	public Optional<Product> getProductByUid(String productUid) {
		return productRepository.findByUid(productUid);
	}
	
	public Optional<Product> getProductByNameAndUser(String name, String uid) {
		return productRepository.findByNameAndAuctionUserUid(name, uid);
	}
}
