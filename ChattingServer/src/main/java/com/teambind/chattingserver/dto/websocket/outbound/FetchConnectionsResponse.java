package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.Connection;
import com.teambind.constant.MessageType;
import lombok.Getter;

import java.util.List;

@Getter
public class FetchConnectionsResponse extends BaseMessage {

	@JsonProperty("connections")
	private final List<Connection> connections;


	public FetchConnectionsResponse(List<Connection> connections) {
		super(MessageType.FETCH_CONNECTIONS_RESPONSE);
		this.connections = connections;
	}

	public List<Connection> getConnections() {
		return connections;
	}
}
