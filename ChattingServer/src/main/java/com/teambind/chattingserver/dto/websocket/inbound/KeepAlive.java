package com.teambind.chattingserver.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.teambind.constant.MessageType;


public class KeepAlive extends BaseRequest {
	@JsonCreator
	public KeepAlive() {
		super(MessageType.KEEP_ALIVE);
	}
}
