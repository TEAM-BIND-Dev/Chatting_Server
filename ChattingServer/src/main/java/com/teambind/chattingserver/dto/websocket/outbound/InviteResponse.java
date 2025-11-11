package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.InviteCode;
import com.teambind.constant.MessageType;
import com.teambind.constant.UserConnectionStatus;
import lombok.Getter;

@Getter
public class InviteResponse extends BaseMessage {
	
	@JsonProperty("inviteCode")
	private final InviteCode inviteCode;
	
	@JsonProperty("status")
	private final UserConnectionStatus status;
	
	
	public InviteResponse(InviteCode inviteCode, UserConnectionStatus status) {
		super(MessageType.INVITE_RESPONSE);
		this.inviteCode = inviteCode;
		this.status = status;
	}
}
