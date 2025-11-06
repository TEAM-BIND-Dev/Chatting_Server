package com.teambind.chattingserver.service;

import com.teambind.auth.dto.Channel;
import com.teambind.auth.dto.ChannelId;
import com.teambind.auth.dto.UserId;
import com.teambind.auth.entity.ChannelEntity;
import com.teambind.auth.entity.UserChannelEntity;
import com.teambind.auth.repository.ChannelRepository;
import com.teambind.auth.repository.UserChannelRepository;
import com.teambind.constant.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChannelService {
	
	private final UserConnectionService userConnectionService;
	private final ChannelRepository channelRepository;
	private final UserChannelRepository userChannelRepository;
	
	public ChannelService(UserConnectionService userConnectionService, ChannelRepository channelRepository, UserChannelRepository userChannelRepository) {
		this.userConnectionService = userConnectionService;
		this.channelRepository = channelRepository;
		this.userChannelRepository = userChannelRepository;
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
	
	
}
