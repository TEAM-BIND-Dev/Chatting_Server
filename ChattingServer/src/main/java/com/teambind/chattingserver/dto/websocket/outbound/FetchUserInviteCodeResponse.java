package com.teambind.chattingserver.dto.websocket.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.InviteCode;
import com.teambind.constant.MessageType;
import lombok.Getter;

@Getter
public class FetchUserInviteCodeResponse extends BaseMessage {

	@JsonProperty("inviteCode")
	private final InviteCode inviteCode;


	public FetchUserInviteCodeResponse(InviteCode inviteCode) {
		super(MessageType.FETCH_USER_INVITE_CODE_RESPONSE);
		this.inviteCode = inviteCode;
	}

	public InviteCode getInviteCode() {
		return inviteCode;
	}
}
