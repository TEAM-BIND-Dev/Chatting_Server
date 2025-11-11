package com.teambind.auth.entity;

import com.teambind.common.config.baseEntity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_channel")
@IdClass(UserChannelId.class)

public class UserChannelEntity extends BaseEntity {
	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@Id
	@Column(name = "channel_id", nullable = false)
	private Long channelId;
	@Column(name = "last_read_msg_seq", nullable = false)
	private Long lastReadMsgSeq;
	
	public UserChannelEntity() {
	}
	
	public UserChannelEntity(Long userId, Long channelId, Long lastReadMsgSeq) {
		this.userId = userId;
		this.channelId = channelId;
		this.lastReadMsgSeq = lastReadMsgSeq;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public Long getChannelId() {
		return channelId;
	}
	
	public Long getLastReadMsgSeq() {
		return lastReadMsgSeq;
	}
	
	public void setLastReadMsgSeq(Long lastReadMsgSeq) {
		this.lastReadMsgSeq = lastReadMsgSeq;
	}
}
