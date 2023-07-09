package edu.flab.global.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Common
	ACCESS_DENIED(401, "C001", "Access is Denied"),
	INVALID_INPUT_VALUE(400, "C002", "Invalid input value"),
	METHOD_NOT_ALLOWED(405, "C003", "Not supported method");

	private final int httpStatusCode;
	private final String code;
	private final String message;

	ErrorCode(final int httpStatusCode, final String code, final String message) {
		this.httpStatusCode = httpStatusCode;
		this.code = code;
		this.message = message;
	}
}
