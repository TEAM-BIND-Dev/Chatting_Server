package com.teambind.messagesystem.service;

import com.teambind.messagesystem.dto.ChannelId;

public class UserService{
	
	private enum Location{
		LOBBY, CHANNEL
	}
	
	private Location userLocation = Location.LOBBY;
	private String username = "";
	private ChannelId channelId;
	
	public boolean isInLobby() {
		return userLocation == Location.LOBBY;
	}
	public boolean isInChannel() {
		return userLocation == Location.CHANNEL;
	}
	
	public void login(String username) {
		this.username = username;
		moveToLobby();
	}
	public void logout() {
		username = "";
		moveToLobby();
	}
	
	public void setInChannel(ChannelId channelId, String username) {
		this.channelId = channelId;
	}
	public ChannelId getChannelId() {
		return channelId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void moveToChannel(ChannelId channelId) {
		this.channelId = channelId;
		this.userLocation = Location.CHANNEL;
	}
	public void moveToLobby() {
		this.channelId = null;
		this.userLocation = Location.LOBBY;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
