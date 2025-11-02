package com.teambind.chattingserver.dto.websocket.outbound;

import com.teambind.auth.dto.InviteCode;
import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;
import lombok.Getter;

public class AcceptResponse extends BaseMessage{
	
	
	private final String username;
	
	
	public AcceptResponse(String username) {
		super(MessageType.ACCEPT_RESPONSE);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
