package com.teambind.auth.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserChannelId implements Serializable {
	private Long channelId;
	private Long userId;
	
	public UserChannelId(Long channelId, Long userId) {
		this.channelId = channelId;
		this.userId = userId;
	}
	
	public UserChannelId() {
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
		UserChannelId userChannelId1 = (UserChannelId) o;
		return Objects.equals(channelId, userChannelId1.channelId) && Objects.equals(userId, userChannelId1.userId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(channelId, userId);
	}
}
