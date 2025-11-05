package com.teambind.messagesystem.dto.websocket.outbound;


import com.teambind.messagesystem.constant.MessageType;

public class FetchUserInviteCodeRequest extends BaseRequest {
	
	
	public FetchUserInviteCodeRequest() {
		super(MessageType.FETCH_USER_INVITE_CODE_REQUEST);
		
		
	}
}
