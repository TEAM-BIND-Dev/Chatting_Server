package com.teambind.auth.repository;

import com.teambind.auth.entity.UserChannelId;
import com.teambind.auth.entity.UserChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface UserChannelRepository extends JpaRepository <UserChannelEntity, UserChannelId> {
	boolean existsByUserIdAndChannelId(@NonNull Long UserId, @NonNull Long channelId);
	
}
