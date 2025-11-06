package com.teambind.messagesystem.dto.websocket.outbound;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;

public class WriteMessageRequest extends BaseRequest {
	@JsonProperty("username")
	private final String username;
	@JsonProperty("content")
	private final String content;


	public WriteMessageRequest(
			String username,
			String content) {
		super(MessageType.WRITE_MESSAGE);
		this.username = username;
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public String getContent() {
		return content;
	}
}
