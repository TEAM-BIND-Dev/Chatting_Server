package com.teambind.chattingserver.dto.websocket.outbound;


import com.teambind.auth.dto.ChannelId;
import com.teambind.constant.MessageType;

public class JoinNotification extends BaseMessage{

	private final ChannelId channelId;
	private final String title;
	
	public JoinNotification(ChannelId channelId, String title) {
		super(MessageType.NOTIFY_JOIN);
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
