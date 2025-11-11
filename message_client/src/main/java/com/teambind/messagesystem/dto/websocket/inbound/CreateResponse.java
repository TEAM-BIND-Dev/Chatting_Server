package com.teambind.messagesystem.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.dto.ChannelId;

public class CreateResponse extends BaseMessage {
	
	private final ChannelId channelId;
	private final String title;
	
	@JsonCreator
	public CreateResponse(
			@JsonProperty("channelId") ChannelId channelId,
			@JsonProperty("title") String title) {
		super(MessageType.CREATE_RESPONSE);
		this.channelId = channelId;
		this.title = title;
	}
	
	public ChannelId getChannelId() {
		return channelId;
	}
	
	public String getTitle() {
		return title;
	}
}
