package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.RejectRequest;
import com.teambind.chattingserver.dto.websocket.outbound.ErrorResponse;
import com.teambind.chattingserver.dto.websocket.outbound.RejectResponse;
import com.teambind.chattingserver.service.UserConnectionService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.Constants;
import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class RejectRequestHandler implements BaseRequestHandler<RejectRequest> {
	
	private final UserConnectionService userConnectionService;
	private final WebSocketSessionManager sessionManager;
	
	public RejectRequestHandler(UserConnectionService userConnectionService, WebSocketSessionManager sessionManager) {
		this.userConnectionService = userConnectionService;
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void handleRequest(WebSocketSession senderSession, RejectRequest request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(Constants.USER_ID.getValue());
		Pair<Boolean, String> result = userConnectionService.reject(senderUserId, request.getUsername());
		if (result.getFirst()) {
			sessionManager.sendMessage(senderSession, new RejectResponse(request.getUsername(), UserConnectionStatus.REJECTED));
		} else {
			String errorMessage = result.getSecond();
			sessionManager.sendMessage(senderSession, new ErrorResponse(MessageType.REJECT_REQUEST, errorMessage));
		}
		
		
	}
}
