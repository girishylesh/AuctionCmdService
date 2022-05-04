package com.eauction.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -383494812811156679L;
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}
