package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.constant.MessageType;
import lombok.Getter;

@Getter
public class ErrorResponse extends BaseMessage {

	@JsonProperty("messageType")
	private final String messageType;

	@JsonProperty("message")
	private final String message;

	public ErrorResponse(String messageType, String message) {
		super(MessageType.ERROR);
		this.messageType = messageType;
		this.message = message;
	}
}
