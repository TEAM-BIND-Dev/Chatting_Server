package com.teambind.auth.repository;

import com.teambind.auth.dto.projection.ChannelTitleProjection;
import com.teambind.auth.entity.ChannelEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {
	
	Optional<ChannelTitleProjection> findChannelTitleByChannelId(@NonNull Long channelId);
	
}
