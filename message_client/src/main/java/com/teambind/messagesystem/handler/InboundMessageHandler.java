package com.teambind.messagesystem.handler;

import com.teambind.messagesystem.dto.websocket.inbound.*;
import com.teambind.messagesystem.dto.websocket.outbound.AcceptRequest;
import com.teambind.messagesystem.service.TerminalService;
import com.teambind.messagesystem.service.UserService;
import com.teambind.messagesystem.util.JsonUtil;

public class InboundMessageHandler {
	
	private final TerminalService terminalService;
	private final UserService userService;
	
	
	public InboundMessageHandler(TerminalService terminalService, UserService userService) {
		this.terminalService = terminalService;
		this.userService = userService;
		
	}
	
	public void handle(String payload)
	{
		JsonUtil.fromJson(payload, BaseMessage.class).ifPresent(message ->{
			if(message instanceof MessageNotification messageNotification){
				message(messageNotification);
			} else if (message instanceof FetchUserInviteCodeResponse fetchUserInviteCodeResponse) {
				fetchUserInviteCode(fetchUserInviteCodeResponse);
			} else if (message instanceof InviteResponse inviteResponse) {
				invite(inviteResponse);
			} else if (message instanceof InviteNotification inviteNotification ) {
				askInvite(inviteNotification);
			} else if (message instanceof AcceptResponse acceptResponse) {
				accept(acceptResponse);
			} else if (message instanceof AcceptNotification acceptNotification) {
				acceptNotification(acceptNotification);
			} else if (message instanceof RejectResponse rejectResponse) {
				reject(rejectResponse);
			} else if (message instanceof DisconnectResponse disconnectResponse) {
				disconnect(disconnectResponse);
			} else if (message instanceof FetchConnectionsResponse fetchConnectionsResponse) {
				fetchConnections(fetchConnectionsResponse);
			} else if (message instanceof CreateResponse createResponse) {
				create(createResponse);
			} else if (message instanceof JoinNotification joinNotification) {
				joinNotification(joinNotification);
			} else if (message instanceof EnterResponse enterResponse) {
				enter(enterResponse);
			} else if (message instanceof ErrorResponse errorResponse) {
				error(errorResponse);
			} else {
				throw new IllegalStateException("unknown message type : " + message.getClass().getName());
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
	
	private void askInvite(InviteNotification inviteNotification)
	{
		terminalService.printSystemMessage(
				"Do you accept %s s connection request?".formatted(inviteNotification.getUsername())
		);
	}
	
	private void accept(AcceptResponse acceptResponse){
		terminalService.printSystemMessage
				("Connected %s".formatted(acceptResponse.getUsername()));
	}
	
	private void acceptNotification(AcceptNotification acceptNotification){
		terminalService.printSystemMessage
				("Connected %s".formatted(acceptNotification.getUsername()));
	}
	private void reject(RejectResponse rejectResponse){
		terminalService.printSystemMessage
				("Reject %s result : %s".formatted(rejectResponse.getUsername(), rejectResponse.getStatus()));
	}
	
	private void disconnect(DisconnectResponse disconnectResponse){
		terminalService.printSystemMessage
				("Disconnect %s result : %s".formatted(disconnectResponse.getUsername(), disconnectResponse.getStatus()));
	}
	private void fetchConnections(FetchConnectionsResponse fetchConnectionsResponse){
		fetchConnectionsResponse.getConnections().forEach(connection -> {
			terminalService.printSystemMessage(
					"%s : %s".formatted(connection.username(), connection.status())
			);
		});
	}
	private void create(CreateResponse createResponse){
		terminalService.printSystemMessage(
				"Created channel %s , %s".formatted(createResponse.getChannelId(), createResponse.getTitle())
		);
	}
	
	private void joinNotification(JoinNotification joinNotification){
		terminalService.printSystemMessage(
				"Joined channel %s , %s".formatted(joinNotification.getChannelId(), joinNotification.getTitle())
		);
	}
	
	private void enter(EnterResponse enterResponse){
		userService.moveToChannel(enterResponse.getChannelId());
		terminalService.printSystemMessage(
				"Enter channel %s".formatted(enterResponse.getChannelId())
		);
	}
	private void error(ErrorResponse errorResponse){
		terminalService.printSystemMessage(
				"Error %s: %s".formatted(errorResponse.getMessageType(), errorResponse.getMessage())
		);
	}
}
