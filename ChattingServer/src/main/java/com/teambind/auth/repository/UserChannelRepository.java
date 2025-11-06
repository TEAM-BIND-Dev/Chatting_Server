package com.teambind.auth.repository;

import com.teambind.auth.entity.ChannelId;
import com.teambind.auth.entity.UserChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChannelRepository extends JpaRepository <UserChannelEntity, ChannelId> {
}
