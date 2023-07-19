package edu.flab.global.vo;

import jakarta.servlet.http.HttpServletRequest;

public record HttpRequestInfo(String method, String requestURI, Object parameter) {

	public HttpRequestInfo(HttpServletRequest request, Object parameter) {
		this(request.getMethod(), request.getRequestURI(), parameter);
	}
}
