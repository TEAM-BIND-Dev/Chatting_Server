package com.teambind.messagesystem.dto.websocket.outbound;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.dto.InviteCode;

public class InviteRequest extends BaseRequest {

	@JsonProperty("userInviteCode")
	private final InviteCode userInviteCode;


	public InviteRequest(
			InviteCode userInviteCode) {
		super(MessageType.INVITE_REQUEST);
		this.userInviteCode = userInviteCode;

	}

	public InviteCode getUserInviteCode() {
		return userInviteCode;
	}
}
