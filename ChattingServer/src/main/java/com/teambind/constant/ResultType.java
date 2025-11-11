package com.teambind.constant;

public enum ResultType {
	SUCCESS("Success."),
	FAIL("Fail."),
	INVALID_ARGS("Invalid args"),
	NOT_FOUND("Not found."),
	ALREADY_JOINED("Already joined."),
	OVER_LIMIT("Over limit."),
	NOT_JOINED("Not joined."),
	NOT_ALLOWED("unconnected users included."),
	;
	
	private final String message;
	
	ResultType(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
