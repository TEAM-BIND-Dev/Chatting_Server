package com.teambind.messagesystem.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseRequest {
	@JsonProperty("type")
	private final String type;
	
	public BaseRequest(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}

