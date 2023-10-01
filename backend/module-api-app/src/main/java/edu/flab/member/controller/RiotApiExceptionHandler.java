package edu.flab.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import edu.flab.web.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RiotApiExceptionHandler {
	/**
	 * 컨트롤러가 javax.validation.Valid or @Validated 으로 유효성 체크, 실패하였을 경우 발생
	 */
	@ExceptionHandler(WebClientResponseException.class)
	protected ResponseEntity<ErrorResponse> handleWebClientRequestException(WebClientResponseException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getMessage());
		return new ResponseEntity<>(errorResponse, e.getStatusCode());
	}
}
