package com.teambind.messagesystem.dto.websocket.outbound;


import com.teambind.messagesystem.constant.MessageType;

public class FetchUserInvitecodeRequest extends BaseRequest {
	
	
	public FetchUserInvitecodeRequest() {
		super(MessageType.FETCH_USER_INVITE_CODE_REQUEST);
		
		
	}
}
