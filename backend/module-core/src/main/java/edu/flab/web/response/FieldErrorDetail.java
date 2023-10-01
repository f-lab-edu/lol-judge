package edu.flab.web.response;

import java.util.List;

import org.springframework.validation.BindingResult;

import jakarta.validation.ConstraintViolationException;

public record FieldErrorDetail(String field, Object value, String reason) implements ErrorDetail {

	public static List<FieldErrorDetail> of(BindingResult bindingResult) {
		return bindingResult.getFieldErrors()
			.stream()
			.map(e -> new FieldErrorDetail(e.getField(), e.getRejectedValue(), e.getDefaultMessage())).toList();
	}

	public static List<FieldErrorDetail> of(ConstraintViolationException constraintViolationException) {
		return constraintViolationException.getConstraintViolations()
			.stream()
			.map(v -> new FieldErrorDetail(v.getPropertyPath().toString(), v.getInvalidValue(), v.getMessage()))
			.toList();
	}
}
