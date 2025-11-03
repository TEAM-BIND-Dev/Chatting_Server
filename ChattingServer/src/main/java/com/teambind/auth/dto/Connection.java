package com.teambind.auth.dto;

import com.teambind.constant.UserConnectionStatus;

public record Connection(
		String username,
		UserConnectionStatus status
) {
}
