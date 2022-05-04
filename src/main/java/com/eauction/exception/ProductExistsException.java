package com.eauction.exception;

public class ProductExistsException extends RuntimeException {

	private static final long serialVersionUID = 8538146778485977614L;

	public ProductExistsException(String message) {
		super(message);
	}

}