package com.teambind.chattingserver.handler.websocket;

import com.teambind.chattingserver.dto.websocket.inbound.WriteMessage;
import com.teambind.chattingserver.dto.websocket.outbound.MessageNotification;
import com.teambind.chattingserver.entity.MessageEntity;
import com.teambind.chattingserver.repository.MessageRepository;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WriteMessageHandler implements BaseRequestHandler<WriteMessage> {
	private final WebSocketSessionManager sessionManager;
	private final MessageRepository messageRepository;
	
	public WriteMessageHandler(WebSocketSessionManager sessionManager, MessageRepository messageRepository) {
		this.sessionManager = sessionManager;
		this.messageRepository = messageRepository;
	}
	
	@Override
	public void handleRequest(WebSocketSession webSocketSession, WriteMessage request) {
		MessageNotification receivedMessage = new MessageNotification(request.getUsername(), request.getContent());
		messageRepository.save(new MessageEntity(receivedMessage.getUsername(), receivedMessage.getContent()));
		sessionManager.getSessions().forEach(
				participantSession -> {
					// Compare by sessionId to handle decorated sessions correctly
					if (!participantSession.getId().equals(webSocketSession.getId())) {
						sessionManager.sendMessage(participantSession, receivedMessage);
					}
				}
		);
	}
}
