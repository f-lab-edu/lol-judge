package edu.flab.web.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Common
	ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "Access is Denied"),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid input value"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Not supported method"),

	// Member
	WRONG_ACCOUNT(HttpStatus.UNAUTHORIZED, "Wrong Email or Password"),
	DUPLICATE_ACCOUNT(HttpStatus.CONFLICT, "Member Already Exists");

	private final HttpStatusCode httpStatusCode;
	private final String message;

	ErrorCode(final HttpStatusCode httpStatusCode, final String message) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}
}
