package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.ChannelId;
import com.teambind.constant.MessageType;


public class MessageNotification extends BaseMessage {

	@JsonProperty("channelId")
	private final ChannelId channelId;
	
	@JsonProperty("username")
	private final String username;

	@JsonProperty("content")
	private final String content;

	public MessageNotification(String username, String content, ChannelId channelId) {
		super(MessageType.NOTIFY_MESSAGE);
		this.channelId = channelId;
		this.username = username;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getUsername() {
		return username;
	}
	public ChannelId getChannelId() {
		return channelId;
	}
}

