package edu.flab.global.response;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

/**
 * 필드 관련 오류가 발생했을 때 뿐만 아니라 오류가 발생하는 경우는 다양하다.
 * 각 상황마다 보여줘야 할 정보는 다르다.
 * 따라서 ErrorDetail 이라는 인터페이스를 정의하고, 이를 정의한 오류 정보를 ErrorResponse 에 담길 수 있도록 수정하였다.
 */

public record ErrorResponse(String status, String message, List<? extends ErrorDetail> errors) {
	private final static String ERROR_STATUS = "error";

	public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
		List<? extends ErrorDetail> fieldErrorDetails = Collections.emptyList();
		if (bindingResult != null && bindingResult.hasErrors()) {
			fieldErrorDetails = FieldErrorDetail.of(bindingResult);
		}
		return new ErrorResponse(ERROR_STATUS, errorCode.getMessage(), fieldErrorDetails);
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(ERROR_STATUS, errorCode.getMessage(), null);
	}

	public static ErrorResponse of(String message) {
		return new ErrorResponse(ERROR_STATUS, message, null);
	}
}
