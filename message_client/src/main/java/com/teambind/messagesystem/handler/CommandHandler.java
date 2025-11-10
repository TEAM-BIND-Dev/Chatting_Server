package com.teambind.messagesystem.handler;


import com.teambind.messagesystem.constant.UserConnectionStatus;
import com.teambind.messagesystem.dto.ChannelId;
import com.teambind.messagesystem.dto.InviteCode;
import com.teambind.messagesystem.dto.websocket.outbound.*;
import com.teambind.messagesystem.service.RestApiService;
import com.teambind.messagesystem.service.TerminalService;
import com.teambind.messagesystem.service.UserService;
import com.teambind.messagesystem.service.WebSocketService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandHandler {
	private final RestApiService restApiService;
	private final UserService userService;
	private final WebSocketService webSocketService;
	private final TerminalService terminalService;
	private final Map<String, Function<String[], Boolean>> commands = new HashMap<>();
	
	public CommandHandler(RestApiService restApiService, UserService userService, WebSocketService webSocketService, TerminalService terminalService) {
		this.restApiService = restApiService;
		this.userService = userService;
		this.webSocketService = webSocketService;
		this.terminalService = terminalService;
		prepareCommands();
	}
	
	public boolean process(String command, String arguments) {
		Function<String[], Boolean> commander = commands.getOrDefault(command, (ignored) -> {
			terminalService.printSystemMessage("Invalid command: %s".formatted(command));
			return true;
		});
		return commander.apply(arguments.split(" "));
	}
	
	private void prepareCommands() {
		commands.put("register", this::register);
		commands.put("unregister", this::unregister);
		commands.put("login", this::login);
		commands.put("logout", this::logout);
		
		commands.put("inviteCode", this::inviteCode);
		commands.put("invite", this::invite);
		commands.put("accept", this::accept);
		commands.put("reject", this::reject);
		
		
		commands.put("clear", this::clear);
		commands.put("create", this::create);
		commands.put("enter", this::enter);
		
		commands.put("pending", this::pending);
		commands.put("connections", this::connections);
		commands.put("disconnect", this::disconnect);
		
		commands.put("exit", this::exit);
		commands.put("help", this::help);
	}
	
	private Boolean register(String[] params) {
		if (userService.isInLobby()&& params.length > 1) {
			if (restApiService.register(params[0], params[1])) {
				terminalService.printSystemMessage("Registered successfully");
			} else {
				terminalService.printSystemMessage("Failed to register");
			}
		}
		
		return true;
	}
	
	private Boolean accept(String[] parms){
		if(parms.length >0){
			webSocketService.sendMessage(new AcceptRequest(parms[0]));
			terminalService.printSystemMessage("Accept accept invite");
		}
		return true;
	}
	private Boolean unregister(String[] params) {
		if(userService.isInLobby()) {
			webSocketService.closeSession();
			if (restApiService.unregister()) {
				terminalService.printSystemMessage("Unregistered successfully");
			} else {
				terminalService.printSystemMessage("Failed to unregister");
			}
		}
		return true;
	}
	
	private Boolean login(String[] params) {
		if (userService.isInLobby() && params.length > 1) {
			if (restApiService.login(params[0], params[1])) {
				if (webSocketService.createSession(restApiService.getSessionId())) {
					userService.login(params[0]);
					terminalService.printSystemMessage("Logged in successfully");
				} else {
					terminalService.printSystemMessage("Failed to login");
				}
			}
		}
		
		return true;
	}
	
	private Boolean connections(String[] params){
		if(userService.isInLobby() ) {
			webSocketService.sendMessage(new FetchConnectionsRequest(UserConnectionStatus.ACCEPTED));
			terminalService.printSystemMessage("Get connection list.");
		}
		return true;
	}
	
	private Boolean pending(String[] params)
	{if(userService.isInLobby()) {
		webSocketService.sendMessage(new FetchConnectionsRequest(UserConnectionStatus.PENDING));
		terminalService.printSystemMessage("Get pending connection list.");
	}
		return true;
	}
	private Boolean inviteCode(String[] params){
		if(userService.isInLobby() ) {
			
			webSocketService.sendMessage(new FetchUserInviteCodeRequest());
			terminalService.printSystemMessage("Request get invite code...");
		}
		return true;
	}
	
	private Boolean disconnect(String[] params){
		if(userService.isInLobby() && params.length >0){
			webSocketService.sendMessage(new DisconnectRequest(params[0]));
			terminalService.printSystemMessage("Disconnect user");
		}
		return true;
	}
	
	private Boolean invite(String[] params) {
		if(params.length > 0)
		{
			webSocketService.sendMessage(new InviteRequest(new InviteCode(params[0])));
			terminalService.printSystemMessage("Request user Invite");
		}
		return true;
	}
	
	private Boolean reject(String[] params) {
		if(userService.isInLobby() && params.length > 0)
		{
			webSocketService.sendMessage(new RejectRequest(params[0]));
			terminalService.printSystemMessage("Reject invite request");
		}
		return true;
	}
	
	
	private Boolean logout(String[] params) {
		webSocketService.closeSession();
		if (restApiService.logout()) {
			userService.logout();
			terminalService.printSystemMessage("Logout successfully");
		} else {
			terminalService.printSystemMessage("Failed to logout");
		}
		return true;
	}
	
	private Boolean clear(String[] params) {
		terminalService.clearTerminal();
		terminalService.printSystemMessage("Terminal cleared");
		return true;
	}
	
	private Boolean exit(String[] params) {
		logout(params);
		terminalService.printSystemMessage("Exiting...");
		return false;
	}
	
	private Boolean create(String[] params){
		if(userService.isInLobby() && params.length > 1) {
			webSocketService.sendMessage(new CreateRequest(params[0], params[1]));
			terminalService.printSystemMessage("Request create channel");
			return false;
		}
		return true;
	}
	
	private Boolean enter(String[] params){
		if(userService.isInLobby() && params.length > 0) {
			try{
				ChannelId channelId = new ChannelId(Long.valueOf(params[0]));
				webSocketService.sendMessage(new EnterRequest(channelId));
				terminalService.printSystemMessage("Enter channel");
				
			}catch (Exception e){
				terminalService.printSystemMessage("Invalid channel id");
				return true;
			}
			return false;
		}
		return true;
	}
	
	private Boolean help(String[] params) {
		terminalService.printSystemMessage(
				"""
						Commands:
						Commands For Lobby
						'/register' Register a new user. ex: /register <username> <password>
						'/unregister' Unregister a user. ex: /unregister
						'/login' Login a user. ex: /login <username> <password>
						'/inviteCode' Get invite code. ex: /inviteCode
						'/create' Create  channel. ex: /create <Title> <Username>
						'/enter' Enter the  channel. ex: /enter <ChannelId>
						
						Commands For Channel
						'/invite' Invite a user to connect. ex: /invite <inviteCode>
						'/accept' Accept the invite request . ex: /accept <InviterUsername>
						'/reject' Reject the invite request . ex: /reject <InviterUsername>
						'/connections' Get connection users. ex: /connections
						'/pending' Get pending connection inviters. ex: /pending
						'/disconnect' Disconnect a user. ex: /disconnect <ConnectedUsername>

						
					
						
						Commands For Lobby/Channel
						'/logout' Logout a user. ex: /logout
						'/clear' Clear terminal. ex: /clear
						'/exit' Exit the program. ex: /exit
						"""
		);
		return true;
	}
	
}
