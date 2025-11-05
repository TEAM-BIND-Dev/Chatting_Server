package com.teambind.messagesystem.dto.websocket.inbound;

import com.teambind.messagesystem.constant.MessageType;
import com.teambind.messagesystem.dto.InviteCode;

public class FetchUserInviteCodeResponse extends BaseMessage {
	
	private final InviteCode inviteCode;
	
	
	public FetchUserInviteCodeResponse(InviteCode inviteCode) {
		super(MessageType.FETCH_USER_INVITE_CODE_RESPONSE);
		this.inviteCode = inviteCode;
	}
	
	public InviteCode getInviteCode() {
		return inviteCode;
	}
}
