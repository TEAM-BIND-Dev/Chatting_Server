package com.teambind.auth.repository;

import com.teambind.auth.dto.projection.UserIdProjection;
import com.teambind.auth.entity.UserChannelEntity;
import com.teambind.auth.entity.UserChannelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserChannelRepository extends JpaRepository<UserChannelEntity, UserChannelId> {
	boolean existsByUserIdAndChannelId(@NonNull Long UserId, @NonNull Long channelId);
	
	List<UserIdProjection> findUserIdByChannelId(@NonNull Long channelId);
	
	
}
