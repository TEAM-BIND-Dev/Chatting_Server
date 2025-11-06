package com.teambind.auth.entity;

import com.teambind.common.config.baseEntity.BaseEntity;
import com.teambind.constant.UserConnectionStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "user_connection")
@IdClass(ChannelId.class)

public class UserChannelEntity extends BaseEntity {
	public UserChannelEntity() {
	}
	
	public UserChannelEntity(Long userId, Long channelId, Long lastReadMsgSeq) {
		UserId = userId;
		this.channelId = channelId;
		this.lastReadMsgSeq = lastReadMsgSeq;
	}
	
	public void setLastReadMsgSeq(Long lastReadMsgSeq) {
		this.lastReadMsgSeq = lastReadMsgSeq;
	}
	
	@Id
	@Column(name = "user_id", nullable = false)
	private Long UserId;
	
	@Id
	@Column(name = "channel_id", nullable = false)
	private Long channelId;
	
	@Column(name = "last_read_msg_seq", nullable = false)
	private Long lastReadMsgSeq;
	
	
	public Long getUserId() {
		return UserId;
	}
	
	public Long getChannelId() {
		return channelId;
	}
	
	public Long getLastReadMsgSeq() {
		return lastReadMsgSeq;
	}
}
