package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.FetchUserInvitecodeRequest;
import com.teambind.chattingserver.dto.websocket.outbound.ErrorResponse;
import com.teambind.chattingserver.dto.websocket.outbound.FetchUserInviteCodeResponse;
import com.teambind.chattingserver.service.UserService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.Constants;
import com.teambind.constant.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class FetchUserInviteCodeHandler implements BaseRequestHandler<FetchUserInvitecodeRequest> {
	
	private final UserService userService;
	private final WebSocketSessionManager sessionManager;
	
	public FetchUserInviteCodeHandler(UserService userService, WebSocketSessionManager sessionManager) {
		this.userService = userService;
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void handleRequest(WebSocketSession senderSession, FetchUserInvitecodeRequest request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(Constants.USER_ID.getValue());
		userService.getInviteCode(senderUserId).ifPresentOrElse(
				inviteCode ->
						sessionManager.sendMessage(
								senderSession, new FetchUserInviteCodeResponse(inviteCode)),
				() ->
						sessionManager.sendMessage
								(senderSession,
										new ErrorResponse(MessageType.FETCH_USER_INVITE_CODE_REQUEST,
												"Fetch user invite code filed")));
		
	}
}
