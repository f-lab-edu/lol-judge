package edu.flab.global.response;

public record SuccessResponse<T>(String status, T data) {
	private final static String SUCCESS_STATUS = "success";

	public static <T> SuccessResponse<T> of(T data) {
		return new SuccessResponse<>(SUCCESS_STATUS, data);
	}
}
