package com.teambind.chattingserver.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.constant.MessageType;

import java.util.List;


public class CreateRqeust extends BaseRequest {
	
	private final String title;
	private final List<String> participateUsernames;
	
	
	@JsonCreator
	public CreateRqeust(
			@JsonProperty("title") String title,
			@JsonProperty("participateUsername") List<String> participateUsernames) {
		super(MessageType.CREATE_REQUEST);
		this.title = title;
		this.participateUsernames = participateUsernames;
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<String> getParticipateUsernames() {
		return participateUsernames;
	}
	
}
