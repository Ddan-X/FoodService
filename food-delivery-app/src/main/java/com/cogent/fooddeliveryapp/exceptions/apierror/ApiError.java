package com.cogent.fooddeliveryapp.exceptions.apierror;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ApiError {
	
	private HttpStatus status;
	
	@JsonFormat(shape = Shape.STRING,pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime localDateTime;
	
	private String message;
	
	private String debugMessage;
	
	private List<ApiSubError> subErrors;//to hold validatinal related errors
	
	private ApiError() {
		//the time of calling this constructor whatever datatime value is there, it will use it
		localDateTime = LocalDateTime.now();
	}
	
	
	public HttpStatus getStatus() {
		return status;
	}


	public void setStatus(HttpStatus status) {
		this.status = status;
	}


	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}


	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getDebugMessage() {
		return debugMessage;
	}


	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}


	public List<ApiSubError> getSubErrors() {
		return subErrors;
	}


	public void setSubErrors(List<ApiSubError> subErrors) {
		this.subErrors = subErrors;
	}


	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	//every field validation in SubError
	//create a subError and add it into the list.
	
	private void addSubError(ApiSubError apiSubError) {
		if(subErrors == null) {
			subErrors = new ArrayList<>();
		}
		
		subErrors.add(apiSubError);
	}
	
	private void addValidationError(String object, String field, Object rejectedValue,String message) {
		addSubError(new ApiValidationError(object,field,rejectedValue,message));
	}
	
	private void addValidationError(String object, String message) {
		addSubError(new ApiValidationError(object,message));
	}
	
	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(),fieldError.getField(),fieldError.getRejectedValue(),fieldError.getDefaultMessage());
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(e->this.addValidationError(e));
	}
	
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(),objectError.getDefaultMessage());
	}
	public void addValidationObejctErrors(List<ObjectError> globalErrors) {
		globalErrors.forEach(e->this.addValidationError(e));
	}
	public void addValidationError(ConstraintViolation<?> cv) {
		this.addValidationError(cv.getRootBeanClass().getName(),
				((PathImpl)(cv.getPropertyPath())).getLeafNode().asString(),
				cv.getInvalidValue(),cv.getMessage());
	}
	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		
		constraintViolations.forEach(e->addValidationError(e));
	}
}
