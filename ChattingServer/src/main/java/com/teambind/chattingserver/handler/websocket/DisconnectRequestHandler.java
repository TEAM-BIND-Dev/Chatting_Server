package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.DisconnectRequest;
import com.teambind.chattingserver.dto.websocket.outbound.DisconnectResponse;
import com.teambind.chattingserver.dto.websocket.outbound.ErrorResponse;
import com.teambind.chattingserver.service.UserConnectionService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.IdKey;
import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class DisconnectRequestHandler implements BaseRequestHandler<DisconnectRequest> {
	
	private final UserConnectionService userConnectionService;
	private final WebSocketSessionManager sessionManager;
	
	public DisconnectRequestHandler(UserConnectionService userConnectionService, WebSocketSessionManager sessionManager) {
		this.userConnectionService = userConnectionService;
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void handleRequest(WebSocketSession senderSession, DisconnectRequest request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(IdKey.USER_ID.getValue());
		Pair<Boolean, String> result = userConnectionService.disconnect(senderUserId, request.getUsername());
		if (result.getFirst()) {
			sessionManager.sendMessage(senderSession, new DisconnectResponse(request.getUsername(), UserConnectionStatus.DISCONNECTED));
		} else {
			String errorMessage = result.getSecond();
			sessionManager.sendMessage(senderSession, new ErrorResponse(MessageType.DISCONNECT_REQUEST, errorMessage));
		}
	}
}
