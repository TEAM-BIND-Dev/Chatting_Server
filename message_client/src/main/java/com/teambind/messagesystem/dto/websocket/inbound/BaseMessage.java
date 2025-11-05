package com.teambind.messagesystem.dto.websocket.inbound;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.teambind.messagesystem.constant.MessageType;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
		@JsonSubTypes.Type(value = InviteResponse.class, name = MessageType.INVITE_RESPONSE),
		@JsonSubTypes.Type(value = AcceptResponse.class, name = MessageType.ACCEPT_RESPONSE),
		@JsonSubTypes.Type(value = RejectResponse.class, name = MessageType.REJECT_RESPONSE),
		@JsonSubTypes.Type(value = DisconnectResponse.class, name = MessageType.DISCONNECT_RESPONSE),
		
		
		@JsonSubTypes.Type(value = AcceptNotification.class, name = MessageType.NOTIFY_ACCEPT),
		@JsonSubTypes.Type(value = MessageNotification.class, name = MessageType.NOTIFY_MESSAGE),
		@JsonSubTypes.Type(value = InviteNotification.class, name = MessageType.ASK_INVITE),
		@JsonSubTypes.Type(value = ErrorResponse.class, name = MessageType.ERROR)
})
public abstract class BaseMessage {
	
	private String type;
	
	public BaseMessage(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
