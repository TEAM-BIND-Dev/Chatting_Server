package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.constant.MessageType;

public class AcceptResponse extends BaseMessage {
	
	
	@JsonProperty("username")
	private final String username;
	
	
	public AcceptResponse(String username) {
		super(MessageType.ACCEPT_RESPONSE);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
