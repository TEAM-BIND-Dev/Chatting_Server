package com.teambind.auth.repository;

import com.teambind.auth.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository< ChannelEntity,Long> {
}
