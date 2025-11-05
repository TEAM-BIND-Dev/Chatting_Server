package com.teambind.messagesystem.dto.websocket.outbound;

import com.teambind.messagesystem.constant.MessageType;


public class KeepAliveRequest extends BaseRequest {
	public KeepAliveRequest() {
		super(MessageType.KEEP_ALIVE);
	}
}
