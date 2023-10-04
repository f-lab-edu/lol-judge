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
	NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "Email not authenticated"),
	DUPLICATE_ACCOUNT(HttpStatus.CONFLICT, "Member Already Exists"),
	DUPLICATE_SUMMONER_NAME(HttpStatus.CONFLICT, "Summoner name already used"),

	// Election
	POINT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "JudgePoint not enough"),
	ALREADY_VOTED(HttpStatus.CONFLICT, "Already voted"),

	// API
	SUMMONER_NOT_FOUND(HttpStatus.NOT_FOUND, "Summoner not found"),
	RIOT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Riot API server error");

	private final HttpStatusCode httpStatusCode;
	private final String message;

	ErrorCode(final HttpStatusCode httpStatusCode, final String message) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}
}
