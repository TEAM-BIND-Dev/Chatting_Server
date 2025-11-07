package com.teambind.chattingserver.dto.websocket.outbound;


import com.teambind.auth.dto.ChannelId;
import com.teambind.constant.MessageType;

public class EnterResponse extends BaseMessage{

	private final ChannelId channelId;
	private final String title;
	
	public EnterResponse(ChannelId channelId, String title) {
		super(MessageType.ENTER_RESPONSE);
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
