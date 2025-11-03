package com.teambind.chattingserver.dto.websocket.outbound;

import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;

public class RejectResponse extends BaseMessage {
	
	
	private final String username;
	private final UserConnectionStatus status;
	
	public RejectResponse(String username, UserConnectionStatus status) {
		super(MessageType.REJECT_RESPONSE);
		this.username = username;
		this.status = status;
	}
	
	public String getUsername() {
		return username;
	}
	
	public UserConnectionStatus getStatus() {
		return status;
	}
}
