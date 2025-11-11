package com.teambind.chattingserver.service;

import com.teambind.auth.dto.ChannelId;
import com.teambind.auth.dto.UserId;
import com.teambind.chattingserver.entity.MessageEntity;
import com.teambind.chattingserver.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class MessageService {
	
	private static final Logger log = LoggerFactory.getLogger(MessageService.class);
	
	private final MessageRepository messageRepository;
	private final ChannelService channelService;
	
	
	public MessageService(MessageRepository messageRepository, ChannelService channelService) {
		this.messageRepository = messageRepository;
		this.channelService = channelService;
	}
	
	
	public void sendMessage(UserId senderUserId, String content, ChannelId channelId, Consumer<UserId> messageSender) {
		
		try {
			messageRepository.save(
					new MessageEntity(senderUserId.id(), content));
		} catch (Exception e) {
			log.error("Send Message failed. cause : {}", e.getMessage());
			return;
		}
		
		List<UserId> participantIds = channelService.getParticipantIds(channelId);
		participantIds.stream().filter(userId -> senderUserId.id() != userId.id())
				.forEach(userId -> {
					if (channelService.isOnline(channelId, userId)) {
						messageSender.accept(userId);
					}
				});
		
	}
}
