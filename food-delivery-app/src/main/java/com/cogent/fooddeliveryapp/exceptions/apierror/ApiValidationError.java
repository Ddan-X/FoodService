package com.cogent.fooddeliveryapp.exceptions.apierror;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)//it will not call super.hashcode/super.equals
@NoArgsConstructor
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;
	public ApiValidationError(String object, String message) {
		// TODO Auto-generated constructor stub
		this.object = object;
		this.message = message;
	}
}
