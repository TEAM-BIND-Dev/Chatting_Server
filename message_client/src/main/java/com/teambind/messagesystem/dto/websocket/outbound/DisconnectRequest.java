package com.teambind.messagesystem.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;


public class DisconnectRequest extends BaseRequest {
	
	@JsonProperty("username")
	private final String username;
	
	
	public DisconnectRequest(String username) {
		super(MessageType.DISCONNECT_REQUEST);
		this.username = username;
		
	}
	
	public String getUsername() {
		return username;
	}
}
