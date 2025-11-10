package com.teambind.messagesystem.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.dto.ChannelId;

public class EnterRequest extends BaseRequest {

	@JsonProperty("channelId")
	private final ChannelId channelId;

	public EnterRequest(ChannelId channelId) {
		super(MessageType.ENTER_REQUEST);
		this.channelId = channelId;
	}

	public ChannelId getChannelId() {
		return channelId;
	}
}