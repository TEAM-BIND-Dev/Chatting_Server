package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.entity.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.AcceptRequest;
import com.teambind.chattingserver.dto.websocket.outbound.AcceptNotification;
import com.teambind.chattingserver.dto.websocket.outbound.AcceptResponse;
import com.teambind.chattingserver.dto.websocket.outbound.ErrorResponse;
import com.teambind.chattingserver.service.UserConnectionService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.Constants;
import com.teambind.constant.MessageType;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;

@Component
public class AcceptRequestHandler implements BaseRequestHandler<AcceptRequest> {
	
	private final UserConnectionService userConnectionService;
	private final WebSocketSessionManager sessionManager;
	
	public AcceptRequestHandler(UserConnectionService userConnectionService, WebSocketSessionManager sessionManager) {
		this.userConnectionService = userConnectionService;
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void handleRequest(WebSocketSession senderSession, AcceptRequest request) {
		UserId acceptorUserId = (UserId) senderSession.getAttributes().get(Constants.USER_ID.getValue());
		Pair<Optional<UserId>, String> result = userConnectionService.accept(acceptorUserId, request.getUsername());
		
		result.getFirst().ifPresentOrElse(
				invitorUserId -> {
					sessionManager.sendMessage(senderSession, new AcceptResponse(request.getUsername()));
					String acceptorUsername = result.getSecond();
					sessionManager.sendMessage(sessionManager.getSession(invitorUserId), new AcceptNotification(acceptorUsername));
				},
				() -> sessionManager.sendMessage(senderSession, new ErrorResponse(MessageType.ACCEPT_REQUEST, result.getSecond())));
	}
}
