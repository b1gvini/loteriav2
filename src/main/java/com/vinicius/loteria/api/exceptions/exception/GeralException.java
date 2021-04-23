package com.vinicius.loteria.api.exceptions.exception;

import org.springframework.http.HttpStatus;

public class GeralException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	
	public GeralException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
