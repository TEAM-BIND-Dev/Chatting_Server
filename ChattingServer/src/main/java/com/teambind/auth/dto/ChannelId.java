package com.teambind.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record ChannelId(
		@JsonValue Long channelId

) {
	@JsonCreator
	public ChannelId {
		if (channelId == null || channelId < 0) {
			throw new IllegalArgumentException("invalid invite code");
		}
	}
}
