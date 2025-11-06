package com.teambind.auth.dto;

import java.util.Objects;

public record Channel(
		ChannelId channelId,
		String title,
		int headCount
) {
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Channel channel = (Channel) o;
		return headCount == channel.headCount && Objects.equals(title, channel.title) && Objects.equals(channelId, channel.channelId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(channelId, title, headCount);
	}
}
