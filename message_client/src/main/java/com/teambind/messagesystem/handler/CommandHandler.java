package com.teambind.messagesystem.handler;


import com.teambind.messagesystem.dto.InviteCode;
import com.teambind.messagesystem.dto.websocket.outbound.FetchUserInviteCodeRequest;
import com.teambind.messagesystem.dto.websocket.outbound.InviteRequest;
import com.teambind.messagesystem.service.RestApiService;
import com.teambind.messagesystem.service.TerminalService;
import com.teambind.messagesystem.service.WebSocketService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandHandler {
	private final RestApiService restApiService;
	private final WebSocketService webSocketService;
	private final TerminalService terminalService;
	private final Map<String, Function<String[], Boolean>> commands = new HashMap<>();
	
	public CommandHandler(RestApiService restApiService, WebSocketService webSocketService, TerminalService terminalService) {
		this.restApiService = restApiService;
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
		
		commands.put("clear", this::clear);
		commands.put("exit", this::exit);
		commands.put("help", this::help);
	}
	
	private Boolean register(String[] params) {
		if (params.length > 1) {
			if (restApiService.register(params[0], params[1])) {
				terminalService.printSystemMessage("Registered successfully");
			} else {
				terminalService.printSystemMessage("Failed to register");
			}
		}
		
		return true;
	}
	
	private Boolean unregister(String[] params) {
		webSocketService.closeSession();
		if (restApiService.unregister()) {
			terminalService.printSystemMessage("Unregistered successfully");
		} else {
			terminalService.printSystemMessage("Failed to unregister");
		}
		
		return true;
	}
	
	private Boolean login(String[] params) {
		if (params.length > 1) {
			if (restApiService.login(params[0], params[1])) {
				if (webSocketService.createSession(restApiService.getSessionId())) {
					terminalService.printSystemMessage("Logged in successfully");
				} else {
					terminalService.printSystemMessage("Failed to login");
				}
			}
		}
		
		return true;
	}
	
	private Boolean inviteCode(String[] params){
		webSocketService.sendMessage(new FetchUserInviteCodeRequest());
		terminalService.printSystemMessage("Request get invite code...");
		return true;
	}
	
	private Boolean invite(String[] params) {
		if(params.length > 1)
		{
			webSocketService.sendMessage(new InviteRequest(new InviteCode(params[0])));
			terminalService.printSystemMessage("Request user Invite");
			return true;
		}
		return true;
	}
	
	
	private Boolean logout(String[] params) {
		webSocketService.closeSession();
		if (restApiService.logout()) {
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
	
	private Boolean help(String[] params) {
		terminalService.printSystemMessage(
				"""
						Commands:
						'/register' Register a new user. ex: /register <username> <password>
						'/unregister' Unregister a user. ex: /unregister
						'/login' Login a user. ex: /login <username> <password>
						'/logout' Logout a user. ex: /logout
						'/inviteCode' Get invite code. ex: /inviteCode
						'/invite' Invite a user to connect. ex: /invite <inviteCode>
						'/clear' Clear terminal. ex: /clear
						'/exit' Exit the program. ex: /exit
						"""
		);
		return true;
	}
	
}
