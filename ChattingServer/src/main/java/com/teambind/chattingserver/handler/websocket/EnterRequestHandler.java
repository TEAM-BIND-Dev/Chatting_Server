package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.Channel;
import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.CreateRqeust;
import com.teambind.chattingserver.dto.websocket.inbound.EnterRequest;
import com.teambind.chattingserver.dto.websocket.outbound.CreateResponse;
import com.teambind.chattingserver.dto.websocket.outbound.EnterResponse;
import com.teambind.chattingserver.dto.websocket.outbound.ErrorResponse;
import com.teambind.chattingserver.dto.websocket.outbound.JoinNotification;
import com.teambind.chattingserver.service.ChannelService;
import com.teambind.chattingserver.service.UserService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.IdKey;
import com.teambind.constant.MessageType;
import com.teambind.constant.ResultType;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Optional;

@Component
public class EnterRequestHandler implements BaseRequestHandler<EnterRequest> {
	
	private final ChannelService channelService;
	private final WebSocketSessionManager sessionManager;
	
	public EnterRequestHandler(ChannelService channelService, WebSocketSessionManager sessionManager) {
		this.channelService = channelService;
		
		this.sessionManager = sessionManager;
	}
	
	
	@Override
	public void handleRequest(WebSocketSession senderSession, EnterRequest request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(IdKey.USER_ID.getValue());
		Pair<Optional<String>, ResultType> result = channelService.enter(request.getChannelId(), senderUserId);
		
		result.getFirst().ifPresentOrElse(
				title -> {
					sessionManager.sendMessage(sessionManager.getSession(senderUserId),
							new EnterResponse(request.getChannelId(), title));
					
				},
				() -> {
					sessionManager.sendMessage(senderSession,
							new ErrorResponse(MessageType.ENTER_REQUEST, result.getSecond().getMessage()));
				}
		);
		
	}
}
