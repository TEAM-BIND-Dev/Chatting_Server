package com.teambind.messagesystem.dto.websocket.inbound;


import com.teambind.messagesystem.constant.MessageType;

public class ErrorResponse extends BaseMessage {
	
	private final String messageType;
	private final String message;
	
	public ErrorResponse(String messageType, String message) {
		super(MessageType.ERROR);
		this.messageType = messageType;
		this.message = message;
	}
}
