package com.teambind.chattingserver.service;

import com.teambind.auth.dto.ChannelId;
import com.teambind.auth.dto.UserId;
import com.teambind.constant.IdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {
	private static final Logger log = LoggerFactory.getLogger(SessionService.class);
	private final SessionRepository<? extends Session> httpSessionRepository;
	
	private final StringRedisTemplate stringRedisTemplate;
	private final String NAMESPACE = "message:user";
	private final long TTL = 300;
	
	
	public SessionService(SessionRepository<? extends Session> httpSessionRepository, StringRedisTemplate stringRedisTemplate) {
		this.httpSessionRepository = httpSessionRepository;
		this.stringRedisTemplate = stringRedisTemplate;
	}
	
	public void refreshTTL(UserId userId, String httpSessionId) {
		String channelIdKey = buildChannelIdKey(userId);
		try {
			Session httpSession = httpSessionRepository.findById(httpSessionId);
			if (httpSession != null) {
				httpSession.setLastAccessedTime(Instant.now());
				stringRedisTemplate.expire(channelIdKey, TTL, TimeUnit.SECONDS);
			}
			
		} catch (Exception e) {
			log.error("REDIS expire failed. key: {} channelId: {}", channelIdKey, httpSessionId);
		}
	}
	
	public boolean setActiveChannel(UserId userId, ChannelId channelId) {
		String channelIdKey = buildChannelIdKey(userId);
		try {
			stringRedisTemplate.opsForValue().set(channelIdKey, channelId.channelId().toString(), TTL, TimeUnit.SECONDS);
			return true;
		} catch (Exception e) {
			log.error("REDIS set failed. key: {} channelId: {}", channelIdKey, channelId);
			return false;
		}
	}
	
	public boolean isOnline(UserId userId, ChannelId channelId) {
		String channelIdKey = buildChannelIdKey(userId);
		try {
			String chId = stringRedisTemplate.opsForValue().get(channelIdKey);
			if (chId != null && chId.equals(channelId.channelId().toString())) {
				return true;
			}
		} catch (Exception e) {
			log.error("REDIS get failed. key: {} channelId: {}", channelIdKey, channelId);
			return false;
		}
		return false;
	}
	
	private String buildChannelIdKey(UserId userId) {
		return "%s:%d:%s".formatted(NAMESPACE, userId.id(), IdKey.CHANNEL_ID.getValue());
	}
	
	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
		
	}
}
