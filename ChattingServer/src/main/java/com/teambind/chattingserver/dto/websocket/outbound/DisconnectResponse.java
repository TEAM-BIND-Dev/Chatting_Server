package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;
import lombok.Getter;

@Getter
public class DisconnectResponse extends BaseMessage {

	@JsonProperty("username")
	private final String username;

	@JsonProperty("status")
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
