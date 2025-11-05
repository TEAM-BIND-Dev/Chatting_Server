package com.teambind.messagesystem.handler;

import com.teambind.messagesystem.dto.websocket.inbound.BaseMessage;
import com.teambind.messagesystem.dto.websocket.inbound.FetchUserInviteCodeResponse;
import com.teambind.messagesystem.dto.websocket.inbound.InviteResponse;
import com.teambind.messagesystem.dto.websocket.inbound.MessageNotification;
import com.teambind.messagesystem.service.TerminalService;
import com.teambind.messagesystem.util.JsonUtil;

public class InboundMessageHandler {
	
	private final TerminalService terminalService;
	
	
	public InboundMessageHandler(TerminalService terminalService) {
		this.terminalService = terminalService;
	}
	
	public void handle(String payload)
	{
		JsonUtil.fromJson(payload, BaseMessage.class).ifPresent(message ->{
			if(message instanceof MessageNotification messageNotification){
				message(messageNotification);
			} else if (message instanceof FetchUserInviteCodeResponse fetchUserInviteCodeResponse) {
				fetchUserInviteCode(fetchUserInviteCodeResponse);
			}
			
		});
	}
	
	private void message(MessageNotification messageNotification)
	{
		terminalService.PrintMessage(messageNotification.getUsername(), messageNotification.getContent());
	}
	
	private void fetchUserInviteCode(FetchUserInviteCodeResponse fetchUserInviteCodeResponse){
		terminalService.printSystemMessage(
				"My invite code is: %s" .formatted(fetchUserInviteCodeResponse.getInviteCode()));
	}
	private void invite(InviteResponse inviteResponse){
		terminalService.printSystemMessage(
				"Invite %s result : %s".formatted(inviteResponse.getInviteCode(), inviteResponse.getStatus())
		);
	}
	
}
