package com.teambind.chattingserver.dto.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teambind.auth.dto.InviteCode;
import com.teambind.constant.MessageType;


public class FetchUserInvitecodeRequest extends BaseRequest {
	
	
	
	@JsonCreator
	public FetchUserInvitecodeRequest() {
		super(MessageType.FETCH_USER_INVITE_CODE_REQUEST);
		
		
	}
}
