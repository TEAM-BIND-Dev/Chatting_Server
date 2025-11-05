package com.teambind.messagesystem.dto.websocket.inbound;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;

public class AcceptNotification extends BaseMessage {
	
	
	private final String username;
	
	
	@JsonCreator
	public AcceptNotification(@JsonProperty("username") String username) {
		super(MessageType.NOTIFY_ACCEPT);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
