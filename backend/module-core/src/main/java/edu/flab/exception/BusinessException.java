package edu.flab.exception;

import edu.flab.web.response.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public BusinessException(ErrorCode errorCode, Throwable t) {
		super(t);
		this.errorCode = errorCode;
	}
}
