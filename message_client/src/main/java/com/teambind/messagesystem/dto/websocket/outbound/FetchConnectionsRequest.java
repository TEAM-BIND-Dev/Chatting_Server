package com.teambind.messagesystem.dto.websocket.outbound;


import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.constant.UserConnectionStatus;

public class FetchConnectionsRequest extends BaseRequest {
	private final UserConnectionStatus status;
	
	public FetchConnectionsRequest(UserConnectionStatus status) {
		super(MessageType.FETCH_CONNECTIONS_REQUEST);
		this.status = status;
	}
	
	public UserConnectionStatus getStatus() {
		return status;
	}
}
