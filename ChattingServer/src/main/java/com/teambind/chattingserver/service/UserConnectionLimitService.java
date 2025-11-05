package com.teambind.chattingserver.service;

import com.teambind.auth.entity.UserConnectionEntity;
import com.teambind.auth.entity.UserEntity;
import com.teambind.auth.entity.UserId;
import com.teambind.auth.repository.UserConnectionRepository;
import com.teambind.auth.repository.UserRepository;
import com.teambind.constant.UserConnectionStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
public class UserConnectionLimitService {
	private final UserRepository userRepository;
	private final UserConnectionRepository userConnectionRepository;
	private int limitConnections = 1_000;
	
	public UserConnectionLimitService(UserRepository userRepository, UserConnectionRepository userConnectionRepository) {
		this.userRepository = userRepository;
		this.userConnectionRepository = userConnectionRepository;
	}
	
	public int getLimitConnections() {
		return limitConnections;
	}
	
	public void setLimitConnections(int limitConnections) {
		this.limitConnections = limitConnections;
	}
	
	@Transactional
	public void accept(UserId accepterUserId, UserId inviterUserId) {
		Long firstUserId = Long.min(accepterUserId.id(), inviterUserId.id());
		Long secondUserId = Long.max(accepterUserId.id(), inviterUserId.id()); // ㄱㅛ착상태 대드락 때문에 이렇게 코딩
		
		
		UserEntity firstUserEntity = userRepository.findForUpdateByUserId(firstUserId).orElseThrow(() -> new EntityNotFoundException("Invalid user id: " + firstUserId));
		UserEntity secondUserEntity = userRepository.findForUpdateByUserId(secondUserId).orElseThrow(() -> new EntityNotFoundException("Invalid user id: " + secondUserId));
		
		UserConnectionEntity userConnectionEntity = userConnectionRepository.findByPartnerUserAIdAndPartnerUserBIdAndStatus(firstUserId, secondUserId, UserConnectionStatus.PENDING).orElseThrow(() -> new EntityNotFoundException("Invalid user connection"));
		
		Function<Long, String> getErrorMessage = userId -> userId.equals(accepterUserId.id()) ? "Connection limit reached." : "Connection limit reached by the other user.";
		int firstConnectionCount = firstUserEntity.getConnectionCount();
		if (firstConnectionCount >= limitConnections) {
			throw new IllegalStateException(getErrorMessage.apply(firstUserId));
		}
		
		int secondConnectionCount = secondUserEntity.getConnectionCount();
		if (secondConnectionCount >= limitConnections) {
			throw new IllegalStateException(getErrorMessage.apply(secondUserId));
		}
		
		firstUserEntity.setConnectionCount(firstConnectionCount + 1);
		secondUserEntity.setConnectionCount(secondConnectionCount + 1);
		userConnectionEntity.setStatus(UserConnectionStatus.ACCEPTED);
		
		userConnectionRepository.save(userConnectionEntity);
		userRepository.save(firstUserEntity);
		userRepository.save(secondUserEntity);
	}
	
	
	@Transactional
	public void disconnect(UserId senderUserId, UserId partnerUserId) {
		Long firstUserId = Long.min(senderUserId.id(), partnerUserId.id());
		Long secondUserId = Long.max(senderUserId.id(), partnerUserId.id()); // 교착상태 대드락 때문에 이렇게 코딩
		
		
		UserEntity firstUserEntity = userRepository.findForUpdateByUserId(firstUserId).orElseThrow(() -> new EntityNotFoundException("Invalid user id: " + firstUserId));
		UserEntity secondUserEntity = userRepository.findForUpdateByUserId(secondUserId).orElseThrow(() -> new EntityNotFoundException("Invalid user id: " + secondUserId));
		
		UserConnectionEntity userConnectionEntity = userConnectionRepository
				.findByPartnerUserAIdAndPartnerUserBIdAndStatus(firstUserId, secondUserId, UserConnectionStatus.ACCEPTED).orElseThrow(() -> new EntityNotFoundException("Invalid user connection"));
		
		Function<Long, String> getErrorMessage = userId -> userId.equals(senderUserId.id())
				? "Connection limit reached." : "Connection limit reached by the other user.";
		int firstConnectionCount = firstUserEntity.getConnectionCount();
		if (firstConnectionCount <= 0) {
			throw new IllegalStateException("Count is already zero. userId = " + firstUserId);
		}
		
		int secondConnectionCount = secondUserEntity.getConnectionCount();
		if (secondConnectionCount <= 0) {
			throw new IllegalStateException("Count is already zero. userId = " + secondUserId);
		}
		
		firstUserEntity.setConnectionCount(firstConnectionCount - 1);
		secondUserEntity.setConnectionCount(secondConnectionCount - 1);
		userConnectionEntity.setStatus(UserConnectionStatus.DISCONNECTED);
		
		userConnectionRepository.save(userConnectionEntity);
		userRepository.save(firstUserEntity);
		userRepository.save(secondUserEntity);
	}
}
