package com.teambind.auth.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChannelId  implements Serializable {
	private Long channelId;
	private Long userId;
	
	public ChannelId(Long channelId, Long userId) {
		this.channelId = channelId;
		this.userId = userId;
	}
	
	public ChannelId() {
	}
	
	public Long getChannelId() {
		return channelId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ChannelId channelId1 = (ChannelId) o;
		return Objects.equals(channelId, channelId1.channelId) && Objects.equals(userId, channelId1.userId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(channelId, userId);
	}
}
