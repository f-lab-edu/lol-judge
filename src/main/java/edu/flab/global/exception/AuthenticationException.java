package edu.flab.global.exception;

import edu.flab.global.response.ErrorCode;

public class AuthenticationException extends BusinessException {
	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
