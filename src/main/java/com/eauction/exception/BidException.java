package com.eauction.exception;

public class BidException extends RuntimeException {

	private static final long serialVersionUID = 5044931907912436300L;
	
	public BidException(String message) {
		super(message);
	}
	
}
