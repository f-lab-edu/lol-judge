package edu.flab.global.response;

import java.util.List;

import org.springframework.validation.BindingResult;

public record FieldErrorDetail(String field, Object value, String reason) implements ErrorDetail {

	public static List<FieldErrorDetail> of(BindingResult bindingResult) {
		return bindingResult.getFieldErrors()
			.stream()
			.map(e -> new FieldErrorDetail(e.getField(), e.getRejectedValue(), e.getDefaultMessage())).toList();
	}
}
