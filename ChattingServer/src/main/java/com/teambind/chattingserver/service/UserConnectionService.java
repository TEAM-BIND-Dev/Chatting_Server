package com.teambind.chattingserver.service;

import com.teambind.auth.dto.InviteCode;
import com.teambind.auth.dto.User;
import com.teambind.auth.entity.UserConnectionEntity;
import com.teambind.auth.entity.UserId;
import com.teambind.auth.repository.UserConnectionRepository;
import com.teambind.constant.UserConnectionStatus;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserConnectionService {
	private final UserService userService;
	private final UserConnectionLimitService userConnectionLimitService;
	private final UserConnectionRepository userConnectionRepository;
	Logger log = LoggerFactory.getLogger(UserConnectionService.class);
	
	public UserConnectionService(UserService userService, UserConnectionLimitService userConnectionLimitService, UserConnectionRepository userConnectionRepository) {
		this.userService = userService;
		this.userConnectionLimitService = userConnectionLimitService;
		this.userConnectionRepository = userConnectionRepository;
	}
	
	public Pair<Optional<UserId>, String> invite(UserId invitoerUserId, InviteCode inviteCode) {
		Optional<User> partner = userService.getUser(inviteCode);
		if (partner.isEmpty()) {
			log.info("invalid invite code. {} from {}", inviteCode, invitoerUserId);
			return Pair.of(Optional.empty(), "invalid invite code");
		}
		
		UserId partnerUserId = partner.get().userId();
		String partnerUserName = partner.get().userName();
		
		if (partnerUserId.equals(invitoerUserId)) {
			log.info("cannot invite self. {} from {}", inviteCode, invitoerUserId);
			return Pair.of(Optional.empty(), "cannot invite self");
		}
		
		UserConnectionStatus status = getStatus(invitoerUserId, partnerUserId);
		return switch (status) {
			case NONE, DISCONNECTED -> {
				Optional<String> invitorUsername = userService.getUsername(invitoerUserId);
				if (invitorUsername.isEmpty()) {
					log.warn("invitor username is empty. {}", invitoerUserId);
					yield Pair.of(Optional.empty(), "invitor username is empty");
				}
				try {
					setStatus(invitoerUserId, partnerUserId, UserConnectionStatus.PENDING);
					yield Pair.of(Optional.of(partnerUserId), invitorUsername.get());
				} catch (Exception e) {
					log.error("setStatus error : {}", e.getMessage());
					yield Pair.of(Optional.empty(), "setStatus error");
				}
			}
			case ACCEPTED -> Pair.of(Optional.of(partnerUserId), "already accepted to " + partnerUserName);
			case PENDING, REJECTED -> {
				log.info("{} invites {} but dose not deliver inviation request", invitoerUserId, partnerUserId);
				yield Pair.of(Optional.of(partnerUserId), "already invited to " + partnerUserName);
			}
			default -> {
				log.error("unknown status : {}", status);
				yield Pair.of(Optional.empty(), "unknown status");
			}
		};
	}
	
	public Pair<Optional<UserId>,String> accept(UserId accepterUserId, String invitorUsername){
		Optional<UserId> userId = userService.getUserId(invitorUsername);
		if(userId.isEmpty())
		{
			log.info("invitor username is empty. {}", invitorUsername);
			return Pair.of(Optional.empty(), "invalid username");
		}
		
		UserId inviterUserId = userId.get();
		
		if(accepterUserId.equals(inviterUserId ))
		{
			log.info("cannot accept self. {}", accepterUserId);
			return Pair.of(Optional.empty(), "cannot accept self");
		}
		
		if(getInvitorUserId(accepterUserId, inviterUserId)
				.filter(invitationSenderUserId -> invitationSenderUserId.equals(inviterUserId))
				.isEmpty()){
			return Pair.of(Optional.empty(), "invalid username");
		}
		
		UserConnectionStatus userConnectionStatus = getStatus(inviterUserId, accepterUserId);
		if(userConnectionStatus.equals(UserConnectionStatus.ACCEPTED))
		{
			log.info("{} already accepted to {}", accepterUserId, inviterUserId);
			return Pair.of(Optional.empty(), "already accepted to " + invitorUsername);
		}
		
		if(!userConnectionStatus.equals(UserConnectionStatus.PENDING))
		{
			return Pair.of(Optional.empty(), "Accept fail");
		}
		
		Optional<String> acceptorUsername = userService.getUsername(accepterUserId);
		if(acceptorUsername.isEmpty()){
			log.info("acceptor username is empty. {}", accepterUserId);
			return Pair.of(Optional.empty(), "Accept fail");
		}
		
		try{
			userConnectionLimitService.accept(accepterUserId, inviterUserId);
			return Pair.of(Optional.of(inviterUserId), acceptorUsername.get());
		} catch (EntityNotFoundException ex)
		{
			log.error("accept error : {}", ex.getMessage());
			return Pair.of(Optional.empty(), "accept error");
		}catch (IllegalStateException ex)
		{
			return Pair.of(Optional.empty(), ex.getMessage());
		}
		
		
		
		
	}
	
	private Optional<UserId> getInvitorUserId(UserId partnerAuserId, UserId partnerBuserId) {
		return userConnectionRepository.findInvitorUserIdByPartnerUserAIdAndPartnerUserBId(
				Long.min(partnerAuserId.id(), partnerBuserId.id()),
				Long.max(partnerAuserId.id(), partnerBuserId.id())).map(invitorUserIdProjection -> new UserId(invitorUserIdProjection.getInvitorUserId()));
	}
	
	private UserConnectionStatus getStatus(UserId invitorUserId, UserId partnerUserId) {
		return userConnectionRepository.findByPartnerUserAIdAndPartnerUserBId(
				Long.min(invitorUserId.id(), partnerUserId.id()),
				Long.max(invitorUserId.id(), partnerUserId.id())).map(status -> UserConnectionStatus.valueOf(status.getStatus())).orElse(
				UserConnectionStatus.NONE
		);
	}
	
	
	@Transactional
	protected void setStatus(UserId invitorUserId, UserId partnerUserId, UserConnectionStatus status) {
		if (status == UserConnectionStatus.ACCEPTED) {
			throw new IllegalArgumentException("Can't set to accepted");
		}
		userConnectionRepository.save(new UserConnectionEntity(
				Long.min(invitorUserId.id(), partnerUserId.id()),
				Long.max(invitorUserId.id(), partnerUserId.id()),
				status, invitorUserId.id()));
	}
}
