package com.teambind.messagesystem.dto.websocket.inbound;


import com.teambind.messagesystem.constant.MessageType;

public class InviteNotification extends BaseMessage {
	
	private final String username;
	
	public InviteNotification(String username) {
		super(MessageType.ASK_INVITE);
		this.username = username;
		
	}
}
