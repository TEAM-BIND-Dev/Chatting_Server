package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.KeepAliveRequest;
import com.teambind.chattingserver.service.SessionService;
import com.teambind.constant.IdKey;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class KeepAliveHandler implements BaseRequestHandler<KeepAliveRequest> {
	private final SessionService sessionService;
	
	public KeepAliveHandler(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	@Override
	public void handleRequest(WebSocketSession webSocketSession, KeepAliveRequest request) {
		UserId senderUserId = (UserId) webSocketSession.getAttributes().get(IdKey.USER_ID.getValue());
		sessionService.refreshTTL(senderUserId,webSocketSession.getId());
	}
}
