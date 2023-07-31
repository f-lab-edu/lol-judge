package edu.flab.exception;

import edu.flab.web.response.ErrorCode;

public class AuthenticationException extends BusinessException {
	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
