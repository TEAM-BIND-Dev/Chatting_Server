package com.teambind.messagesystem.dto.websocket.outbound;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.dto.ChannelId;

public class WriteMessage extends BaseRequest {
	@JsonProperty("channelId")
	private final ChannelId channelId;
	
	@JsonProperty("content")
	private final String content;
	
	
	public WriteMessage(
			ChannelId channelId,
			String content) {
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
