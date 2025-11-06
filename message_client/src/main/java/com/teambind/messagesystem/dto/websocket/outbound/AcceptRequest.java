package com.teambind.messagesystem.dto.websocket.outbound;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;

public class AcceptRequest extends BaseRequest {

	@JsonProperty("username")
	private final String username;


	public AcceptRequest(
			String username) {
		super(MessageType.ACCEPT_REQUEST);
		this.username = username;

	}

	public String getUsername() {
		return username;
	}
}
