package com.teambind.messagesystem.dto.websocket.inbound;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.constant.UserConnectionStatus;
import com.teambind.messagesystem.dto.InviteCode;

public class InviteResponse extends BaseMessage {
	
	private final InviteCode inviteCode;
	private final UserConnectionStatus status;
	
	
	@JsonCreator
	public InviteResponse(@JsonProperty("inviteCode") InviteCode inviteCode, @JsonProperty("status") UserConnectionStatus status) {
		super(MessageType.INVITE_RESPONSE);
		this.inviteCode = inviteCode;
		this.status = status;
	}
	
	public UserConnectionStatus getStatus() {
		return status;
	}
	
	public InviteCode getInviteCode() {
		return inviteCode;
	}
}
