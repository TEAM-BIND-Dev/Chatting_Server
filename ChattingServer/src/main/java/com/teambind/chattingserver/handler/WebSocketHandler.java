package com.teambind.chattingserver.handler;

import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.dto.websocket.inbound.BaseRequest;
import com.teambind.chattingserver.session.WebSocketSessionManager;
import com.teambind.constant.IdKey;
import com.teambind.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
	private final JsonUtil jsonUtil;
	private final WebSocketSessionManager sessionManager;
	private final RequestDispatcher requestDispatcher;
	
	public WebSocketHandler(JsonUtil jsonUtil, WebSocketSessionManager sessionManager, RequestDispatcher requestDispatcher) {
		this.jsonUtil = jsonUtil;
		this.sessionManager = sessionManager;
		this.requestDispatcher = requestDispatcher;
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
		log.info("Connection Closed: {} from{}", status, session.getId());
		UserId userId = (UserId) session.getAttributes().get(IdKey.USER_ID.getValue());
		sessionManager.closeSession(userId);
		log.warn("not exist empty session : {}", session.getId());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession senderSession, TextMessage message) {
		String payload = message.getPayload();
		log.info("Message Received: {} from {}", payload, senderSession.getId());
		jsonUtil.fromJson(payload, BaseRequest.class).ifPresent(baseRequest -> {
			requestDispatcher.dispatch(senderSession, baseRequest);
		});
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		log.error("TransPortError: {} , from : {}", exception.getMessage(), session.getId());
		UserId userId = (UserId) session.getAttributes().get(IdKey.USER_ID.getValue());
		sessionManager.closeSession(userId);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established: {}", session.getId());
		//TODO : overflowStrategy And Param
		ConcurrentWebSocketSessionDecorator concurrentWebSocketSessionDecorator =
				new ConcurrentWebSocketSessionDecorator(session, 5000, 100 * 1024);
		
		UserId userId = (UserId) session.getAttributes().get(IdKey.USER_ID.getValue());
		sessionManager.putSession(userId, concurrentWebSocketSessionDecorator);
	}
	
}
