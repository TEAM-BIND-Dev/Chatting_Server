package com.teambind.chattingserver.dto.websocket.outbound;

import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;
import lombok.Getter;

@Getter
public class DisconnectResponse extends BaseMessage {
	
	private final String username;
	private final UserConnectionStatus status;
	
	public DisconnectResponse(String username, UserConnectionStatus status) {
		super(MessageType.DISCONNECT_RESPONSE);
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
