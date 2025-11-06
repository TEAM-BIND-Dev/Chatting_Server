package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.constant.MessageType;

public class AcceptNotification extends BaseMessage {


	@JsonProperty("username")
	private final String username;


	public AcceptNotification(String username) {
		super(MessageType.NOTIFY_ACCEPT);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
