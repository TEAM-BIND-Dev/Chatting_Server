package com.teambind.messagesystem.dto.websocket.inbound;

import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.dto.Connection;

import java.util.List;

public class FetchConnectionsResponse extends BaseMessage {
	private final List<Connection> connections;
	
	
	public FetchConnectionsResponse(List<Connection> connections) {
		super(MessageType.FETCH_CONNECTIONS_RESPONSE);
		this.connections = connections;
	}
	
	public List<Connection> getConnections() {
		return connections;
	}
}
