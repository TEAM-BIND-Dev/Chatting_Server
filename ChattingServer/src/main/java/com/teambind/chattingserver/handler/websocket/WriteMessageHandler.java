package com.teambind.chattingserver.handler.websocket;

import com.teambind.auth.dto.ChannelId;
import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.WriteMessage;
import com.teambind.chattingserver.dto.websocket.outbound.MessageNotification;
import com.teambind.chattingserver.entity.MessageEntity;
import com.teambind.chattingserver.repository.MessageRepository;
import com.teambind.chattingserver.service.MessageService;
import com.teambind.chattingserver.service.UserService;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.IdKey;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WriteMessageHandler implements BaseRequestHandler<WriteMessage> {
	private final WebSocketSessionManager sessionManager;
	private final MessageService messageService;
	private final UserService userService;
	
	public WriteMessageHandler(WebSocketSessionManager sessionManager, MessageService messageService, UserService userService) {
		this.sessionManager = sessionManager;
		this.messageService = messageService;
		this.userService = userService;
	}
	
	
	
	@Override
	public void handleRequest(WebSocketSession senderSession, WriteMessage request) {
		UserId senderUserId = (UserId) senderSession.getAttributes().get(IdKey.USER_ID.getValue());
		ChannelId channelId = request.getChannelId();
		String content = request.getContent();
		
		String senderUsername = userService.getUsername(senderUserId).orElse("unknown");
		messageService.sendMessage(senderUserId, content, channelId, (userId) -> {
			WebSocketSession participantSession = sessionManager.getSession(userId);
			MessageNotification messageNotification = new MessageNotification(senderUsername, content, channelId);
			if(participantSession != null)
			{
				sessionManager.sendMessage(participantSession, messageNotification);
			}
		});
		
	}
}
