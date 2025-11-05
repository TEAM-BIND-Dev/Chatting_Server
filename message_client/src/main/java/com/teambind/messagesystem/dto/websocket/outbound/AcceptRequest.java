package com.teambind.messagesystem.dto.websocket.outbound;


import com.teambind.messagesystem.constant.MessageType;

public class AcceptRequest extends BaseRequest {
	
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
