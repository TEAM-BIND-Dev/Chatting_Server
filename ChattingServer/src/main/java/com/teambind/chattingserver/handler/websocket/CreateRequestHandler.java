package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.Channel;
import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.CreateRqeust;
import com.teambind.chattingserver.dto.websocket.outbound.CreateResponse;
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
public class CreateRequestHandler implements BaseRequestHandler<CreateRqeust> {
	
	private final ChannelService channelService;
	private final UserService userService;
	private final WebSocketSessionManager sessionManager;
	
	public CreateRequestHandler(ChannelService channelService, UserService userService, WebSocketSessionManager sessionManager) {
		this.channelService = channelService;
		this.userService = userService;
		this.sessionManager = sessionManager;
	}
	
	
	@Override
	public void handleRequest(WebSocketSession senderSession, CreateRqeust request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(IdKey.USER_ID.getValue());
		
		Optional<UserId> userId = userService.getUserId(request.getParticipateUsername());
		if (userId.isEmpty()) {
			sessionManager.sendMessage(senderSession, new ErrorResponse(MessageType.CREATE_REQUEST, ResultType.NOT_FOUND.getMessage()));
			return;
		}
		
		
		UserId participateUserId = userId.get();
		Pair<Optional<Channel>, ResultType> result;
		
		try {
			result = channelService.creat(senderUserId, participateUserId, request.getTitle());
		} catch (Exception ex) {
			sessionManager.sendMessage(senderSession, new ErrorResponse(MessageType.CREATE_REQUEST, ResultType.FAIL.getMessage()));
			return;
		}
		
		
		result.getFirst().ifPresentOrElse(
				channel -> {
					sessionManager.sendMessage(
							senderSession,
							new CreateResponse(channel.channelId(), channel.title()));
					sessionManager.sendMessage(
							sessionManager.getSession(participateUserId),
							new JoinNotification(channel.channelId(), channel.title()));
				},
				() -> sessionManager.sendMessage(
						senderSession,
						new ErrorResponse(MessageType.CREATE_REQUEST, ResultType.FAIL.getMessage())));
		
		
	}
}
