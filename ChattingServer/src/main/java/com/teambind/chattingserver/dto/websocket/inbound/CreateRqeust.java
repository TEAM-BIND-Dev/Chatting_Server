package com.teambind.chattingserver.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.InviteCode;
import com.teambind.constant.MessageType;


public class CreateRqeust extends BaseRequest {
	
	private final String title;
	private final String participateUsername;
	
	
	
	@JsonCreator
	public CreateRqeust(
			@JsonProperty("title") String title,
			@JsonProperty("participateUsername") String participateUsername) {
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
