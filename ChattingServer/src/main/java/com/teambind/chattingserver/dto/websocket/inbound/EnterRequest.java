package com.teambind.chattingserver.dto.websocket.inbound;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.ChannelId;
import com.teambind.chattingserver.dto.websocket.outbound.BaseMessage;
import com.teambind.constant.MessageType;

public class EnterRequest extends BaseMessage {

	private final ChannelId channelId;
	
	@JsonCreator
	public EnterRequest(@JsonProperty("channelId") ChannelId channelId) {
		super(MessageType.ENTER_REQUEST);
		this.channelId = channelId;
	}
	public ChannelId getChannelId() {
		return channelId;
	}
}
