package com.teambind.messagesystem.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;

public class CreateRequest extends BaseRequest {
	
	@JsonProperty("title")
	private final String title;
	
	@JsonProperty("participateUsername")
	private final String participateUsername;
	
	public CreateRequest(String title, String participateUsername) {
		super(MessageType.CREATE_REQUEST);
		this.title = title;
		this.participateUsername = participateUsername;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getParticipateUsername() {
		return participateUsername;
	}
}
