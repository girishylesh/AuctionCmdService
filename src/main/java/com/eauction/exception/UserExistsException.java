package com.eauction.exception;

public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = 7824251895976127471L;

	public UserExistsException(String message) {
		super(message);
	}

}
