package edu.flab.global.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Common
	ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "C001", "Access is Denied"),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "Invalid input value"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "Not supported method"),

	// Member
	PASSWORD_NOT_VALID(HttpStatus.UNAUTHORIZED, "M002", "Invalid Password");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	ErrorCode(final HttpStatusCode httpStatusCode, final String code, final String message) {
		this.httpStatusCode = httpStatusCode;
		this.code = code;
		this.message = message;
	}
}
