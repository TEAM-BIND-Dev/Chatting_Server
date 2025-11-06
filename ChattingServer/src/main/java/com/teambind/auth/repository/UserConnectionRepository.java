package com.teambind.auth.repository;

import com.teambind.auth.dto.projection.InvitorUserIdProjection;
import com.teambind.auth.dto.projection.UserConnectionStatusProjection;
import com.teambind.auth.dto.projection.UserIdUsernameInvitorUserIdProjection;
import com.teambind.auth.entity.UserConnectionEntity;
import com.teambind.auth.entity.UserConnectionId;
import com.teambind.constant.UserConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnectionEntity, UserConnectionId> {
	Optional<UserConnectionStatusProjection> findByPartnerUserAIdAndPartnerUserBId(
			@NonNull Long partnerUserAId,
			@NonNull Long partnerUserBId);
	
	Optional<UserConnectionEntity> findByPartnerUserAIdAndPartnerUserBIdAndStatus(
			@NonNull Long partnerUserAId,
			@NonNull Long partnerUserBId,
			@NonNull UserConnectionStatus status);
	
	Optional<InvitorUserIdProjection> findInvitorUserIdByPartnerUserAIdAndPartnerUserBId(@NonNull Long partnerUserAId, @NonNull Long partnerUserBId);
	
	
	@Query(
			"SELECT u.partnerUserBId AS userId, userB.username AS username , u.invitorUserId as invitorUserId "
					+ "FROM UserConnectionEntity u "
					+ "INNER JOIN UserEntity userB ON u.partnerUserBId = userB.userId "
					+ "where u.partnerUserAId = :userId AND u.status = :status")
	List<UserIdUsernameInvitorUserIdProjection> findUserConnectionPartnerAUserIdUserIdANdStatus(@Param("userId") Long userId, @Param("status") UserConnectionStatus status);
	
	@Query(
			"SELECT u.partnerUserAId AS userId, userA.username as username , u.invitorUserId as invitorUserId "
					+ "FROM UserConnectionEntity u "
					+ "INNER JOIN UserEntity userA ON u.partnerUserAId = userA.userId "
					+ "where u.partnerUserBId = :userId AND u.status = :status")
	List<UserIdUsernameInvitorUserIdProjection> findUserConnectionPartnerBUserIdUserIdANdStatus(@Param("userId") Long userId, @Param("status") UserConnectionStatus status);
	
	
}
