package com.cogent.fooddeliveryapp.exceptions;

public class NoDataFoundException extends RuntimeException {

	public NoDataFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return super.getMessage() ;
	}
	
}
