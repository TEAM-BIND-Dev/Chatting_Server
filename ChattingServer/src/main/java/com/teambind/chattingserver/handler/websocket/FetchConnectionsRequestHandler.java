package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.Connection;
import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.FetchConnectionsRequest;
import com.teambind.chattingserver.dto.websocket.outbound.FetchConnectionsResponse;
import com.teambind.chattingserver.service.UserConnectionService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class FetchConnectionsRequestHandler implements BaseRequestHandler<FetchConnectionsRequest> {
	
	private final UserConnectionService userConnectionService;
	private final WebSocketSessionManager sessionManager;
	
	public FetchConnectionsRequestHandler(UserConnectionService userConnectionService, WebSocketSessionManager sessionManager) {
		this.userConnectionService = userConnectionService;
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void handleRequest(WebSocketSession senderSession, FetchConnectionsRequest request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(Constants.USER_ID.getValue());
		List<Connection> connections = userConnectionService.getUsersByStatus(senderUserId, request.getStatus()).stream().map(
				user -> new Connection(user.userName(), request.getStatus())).toList();
		
		sessionManager.sendMessage(senderSession, new FetchConnectionsResponse(connections));
		
	}
}
