package com.teambind.auth.entity;

import com.teambind.common.config.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "channel")

@ToString
public class ChannelEntity extends BaseEntity {
	
	
	@Column(name = "channel_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long channelId;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "channel_invite_code", nullable = false)
	private String channelInviteCode;
	
	@Column(name = "head_count", nullable = false)
	private Long headCount;
	
	
	protected ChannelEntity() {
		// JPA requires a no-arg constructor
	}
	
	
	public ChannelEntity(Long headCount, String title) {
		this.headCount = headCount;
		this.title = title;
		// JPA requires a no-arg constructor
	}
	
	public Long getChannelId() {
		return channelId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getChannelInviteCode() {
		return channelInviteCode;
	}
	
	public Long getHeadCount() {
		return headCount;
	}
	
	public void setHeadCount(Long headCount) {
		this.headCount = headCount;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ChannelEntity that = (ChannelEntity) o;
		return Objects.equals(channelId, that.channelId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(channelId);
	}
}


