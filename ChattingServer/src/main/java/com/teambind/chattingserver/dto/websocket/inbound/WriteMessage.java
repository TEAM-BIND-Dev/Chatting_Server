package com.teambind.chattingserver.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.ChannelId;
import com.teambind.constant.MessageType;


public class WriteMessage extends BaseRequest {
	private final ChannelId channelId;
	
	private final String content;
	
	@JsonCreator
	public WriteMessage(
			@JsonProperty("channelId") ChannelId channelId,
			
			@JsonProperty("content") String content) {
		super(MessageType.WRITE_MESSAGE);
		
		this.channelId = channelId;
		this.content = content;
	}
	
	
	public String getContent() {
		return content;
	}
	
	public ChannelId getChannelId() {
		return channelId;
	}
}
