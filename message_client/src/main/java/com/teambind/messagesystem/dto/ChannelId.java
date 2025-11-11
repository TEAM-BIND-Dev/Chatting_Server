package com.teambind.messagesystem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record ChannelId(
		@JsonValue Long channelId

) {
	@JsonCreator
	public ChannelId {
		if (channelId == null || channelId < 0) {
			throw new IllegalArgumentException("invalid channel id");
		}
	}
}
