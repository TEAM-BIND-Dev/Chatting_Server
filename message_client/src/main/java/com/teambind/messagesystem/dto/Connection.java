package com.teambind.messagesystem.dto;


import com.teambind.messagesystem.constant.UserConnectionStatus;

public record Connection(
		String username,
		UserConnectionStatus status
) {
}
