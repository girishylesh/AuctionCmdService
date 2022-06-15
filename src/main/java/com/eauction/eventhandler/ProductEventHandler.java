package com.eauction.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eauction.entity.Product;
import com.eauction.events.ProductCreatedEvent;
import com.eauction.events.ProductDeletedEvent;
import com.eauction.repository.ProductRepository;

@Component
public class ProductEventHandler {

	@Autowired
	private ProductRepository productRepository;

	@EventHandler
	public void productCreatedEvent(ProductCreatedEvent productCreatedEvent) {
		Product product = new Product();
		BeanUtils.copyProperties(productCreatedEvent, product);
		productRepository.save(product).getProductId();
	}
	
	@EventHandler
	public void productDeletedEvent(ProductDeletedEvent productDeletedEvent) {
		productRepository.deleteByUid(productDeletedEvent.getProductUid());
	}
}
