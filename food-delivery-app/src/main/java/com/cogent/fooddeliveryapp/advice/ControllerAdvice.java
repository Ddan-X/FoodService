package com.cogent.fooddeliveryapp.advice;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cogent.fooddeliveryapp.exceptions.IdNotFoundException;
import com.cogent.fooddeliveryapp.exceptions.NameAlreadyExistsException;
import com.cogent.fooddeliveryapp.exceptions.NoDataFoundException;
import com.cogent.fooddeliveryapp.exceptions.apierror.ApiError;

import net.bytebuddy.utility.privilege.GetMethodAction;



@org.springframework.web.bind.annotation.ControllerAdvice
//handle all exceptions which are thrown by controller/restController using throws
public class ControllerAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(NameAlreadyExistsException.class)//this is reponsible for handing NameAlreadyExistsException
	public ResponseEntity<?> nameAlreadyExistsException(NameAlreadyExistsException e){
		Map<String,String> map = new HashMap<>();
		map.put("message", "name already exists");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"name already exists",e);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> NoDataFoundException(NoDataFoundException e){
		Map<String,String> map = new HashMap<>();
		map.put("message", "no user data found");
		//HttpStatus.NO_content is for delete
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,e.getMessage(),e);
		return buildResponseEntity(apiError);
	}
	
//	@ExceptionHandler(IdNotFoundException.class)
//	public ResponseEntity<?> idNotFoundException(IdNotFoundException e){
//		Map<String,String> map = new HashMap<>();
//		map.put("message", "role id not found exception");
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"role id not found exception",e);
//		return buildResponseEntity(apiError);
//	}
	
	
	//work for @valid value(post method, if need int but give as string)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		System.out.println("handleMethodArgumentNotValid");
		ApiError apiError = new ApiError(status);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getFieldErrors());
		apiError.addValidationObejctErrors(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(apiError);
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
		return new ResponseEntity<Object>(apiError,apiError.getStatus());
	}
	
	//catch the argument type exception,
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
		System.out.println("handleMethodArgumentTypeMismatch");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		apiError.setDebugMessage(e.getRequiredType().getName());
		return buildResponseEntity(apiError);
	}	
	
	//@Validated (like min.. max..Constraint)
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleMethodConstraintViolationException(ConstraintViolationException e) {
		System.out.println("handleMethod ConstraintViolationException");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		return buildResponseEntity(apiError);
	}	
	
	//default handle, if no other exceptions handle, this one will come
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<?> handleMethodException(Exception e) {
		System.out.println("handleMethodException");
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
		apiError.setMessage(e.getMessage());
		return buildResponseEntity(apiError);
	}	
	
	
}