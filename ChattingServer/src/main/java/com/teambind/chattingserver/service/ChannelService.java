package com.teambind.chattingserver.service;

import com.teambind.auth.dto.Channel;
import com.teambind.auth.dto.ChannelId;
import com.teambind.auth.dto.UserId;
import com.teambind.auth.dto.projection.ChannelTitleProjection;
import com.teambind.auth.entity.ChannelEntity;
import com.teambind.auth.entity.UserChannelEntity;
import com.teambind.auth.repository.ChannelRepository;
import com.teambind.auth.repository.UserChannelRepository;
import com.teambind.constant.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
	private static final Logger log = LoggerFactory.getLogger(ChannelService.class);
	private final ChannelRepository channelRepository;
	private final UserChannelRepository userChannelRepository;
	private final SessionService sessionService;
	
	public ChannelService(ChannelRepository channelRepository, UserChannelRepository userChannelRepository, SessionService sessionService) {

		this.channelRepository = channelRepository;
		this.userChannelRepository = userChannelRepository;
		this.sessionService = sessionService;
	}
	
	
	public List<UserId> getParticipantIds(ChannelId channelId)
	{
		return userChannelRepository.findUserIdByChannelId(channelId.channelId())
				.stream().map(
						userId -> new UserId(userId.getUserId())
				).toList();
		
	}
	
	
	public boolean isOnline(ChannelId channelId, UserId userId) {
		return sessionService.isOnline(userId, channelId);
	}
	
	@Transactional
	public Pair<Optional<Channel>, ResultType> creat(UserId senderUserId, UserId participantId, String title) {
		if(title != null ||title.isEmpty())
		{
			log.warn("Invalid args : title is empty.");
			return Pair.of(Optional.empty(), ResultType.INVALID_ARGS);
		}
		
		try{
			final Long HEAD_COUNT =2L;
			ChannelEntity channelEntity = channelRepository.save(
					new ChannelEntity(HEAD_COUNT,title));
			
			Long channelId = channelEntity.getChannelId();
			List<UserChannelEntity> userChannelEntities = List.of(
					new UserChannelEntity(senderUserId.id(), channelId, 0L),
					new UserChannelEntity(participantId.id(), channelId, 0L)
			);
			userChannelRepository.saveAll(userChannelEntities);
			Channel channel = new Channel( new ChannelId(channelId), title, HEAD_COUNT);
			return Pair.of(Optional.of(channel), ResultType.SUCCESS);
			
		}catch (Exception e) {
			log.error("create creat faild. cause : {}", e.getMessage());
			return Pair.of(Optional.empty(), ResultType.FAIL);
		}
	}
	
	public boolean isJoined(ChannelId channelId, UserId userId) {
		return userChannelRepository.existsByUserIdAndChannelId(userId.id(), channelId.channelId());
		
	}
	
	public Pair<Optional<String>, ResultType> enter(ChannelId channelId, UserId userId) {
		if (!isJoined(channelId, userId)) {
			return Pair.of(Optional.empty(), ResultType.INVALID_ARGS);
		}
		
		Optional<String> title = channelRepository.findChannelTitleByChannelId(channelId.channelId()).map(ChannelTitleProjection::getTitle);
		;
		
		if (title.isEmpty()) {
			log.warn("Enter Channel failed. Channel does not exist. ChannelId : {} , UserId : {}", channelId.channelId(), userId.id());
			return Pair.of(Optional.empty(), ResultType.NOT_FOUND);
		}
		
		if (sessionService.setActiveChannel(userId, channelId)) {
			return Pair.of(title, ResultType.SUCCESS);
		}
		
		log.warn("Enter Channel failed. Channel does not exist. ChannelId : {} , UserId : {}", channelId.channelId(), userId.id());
		return Pair.of(Optional.empty(), ResultType.FAIL);
	}
	
	
}
