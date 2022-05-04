package com.eauction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException unfe) {
		return new ResponseEntity<>(unfe.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = UserExistsException.class)
	public ResponseEntity<Object> exception(UserExistsException uee) {
		return new ResponseEntity<>(uee.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = ProductExistsException.class)
	public ResponseEntity<Object> exception(ProductExistsException pee) {
		return new ResponseEntity<>(pee.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<Object> exception(ProductNotFoundException pnfe) {
		return new ResponseEntity<>(pnfe.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = BidException.class)
	public ResponseEntity<Object> exception(BidException be) {
		return new ResponseEntity<>(be.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
