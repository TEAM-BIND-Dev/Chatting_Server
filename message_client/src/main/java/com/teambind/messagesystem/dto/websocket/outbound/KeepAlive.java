package com.teambind.messagesystem.dto.websocket.outbound;


import com.teambind.messagesystem.constant.MessageType;

public class KeepAlive extends BaseRequest {
	
	public KeepAlive() {
		super(MessageType.KEEP_ALIVE);
	}
}
