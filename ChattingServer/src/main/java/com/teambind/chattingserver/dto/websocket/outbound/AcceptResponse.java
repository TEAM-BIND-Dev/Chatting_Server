package com.teambind.chattingserver.dto.websocket.outbound;

import com.teambind.constant.MessageType;

public class AcceptResponse extends BaseMessage {
	
	
	private final String username;
	
	
	public AcceptResponse(String username) {
		super(MessageType.ACCEPT_RESPONSE);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
