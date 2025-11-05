package com.teambind.messagesystem.handler;

import com.teambind.messagesystem.dto.websocket.inbound.BaseMessage;
import com.teambind.messagesystem.dto.websocket.inbound.MessageNotification;
import com.teambind.messagesystem.dto.websocket.outbound.WriteMessageRequest;
import com.teambind.messagesystem.service.TerminalService;
import com.teambind.messagesystem.util.JsonUtil;
import jakarta.websocket.MessageHandler;

public class WebSocketMessageHandler implements MessageHandler.Whole<String> {
	
	private final TerminalService terminalService;
	private final InboundMessageHandler inboundMessageHandler;
	public WebSocketMessageHandler(TerminalService terminalService, InboundMessageHandler inboundMessageHandler) {
		this.terminalService = terminalService;
		this.inboundMessageHandler = inboundMessageHandler;
	}
	
	
	@Override
	public void onMessage(String payload) {
		inboundMessageHandler.handle(payload);
		
	}
}
