package edu.flab.global.response;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

public record ErrorResponse(String status, String code, String message, List<FieldErrorDetail> errors) {
	private final static String ERROR_STATUS = "error";

	public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
		List<FieldErrorDetail> fieldErrorDetails = Collections.emptyList();
		if (bindingResult != null && bindingResult.hasErrors()) {
			fieldErrorDetails = FieldErrorDetail.of(bindingResult);
		}
		return new ErrorResponse(ERROR_STATUS, errorCode.getCode(), errorCode.getMessage(), fieldErrorDetails);
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return of(errorCode, null);
	}
}
