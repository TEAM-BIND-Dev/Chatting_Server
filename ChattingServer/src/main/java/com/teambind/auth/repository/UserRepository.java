package com.teambind.auth.repository;

import com.teambind.auth.dto.projection.CountProjection;
import com.teambind.auth.dto.projection.InviteCodeProjection;
import com.teambind.auth.dto.projection.UserIdProjection;
import com.teambind.auth.dto.projection.UsernameProjection;
import com.teambind.auth.entity.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(@NonNull String username);
	
	Optional<UsernameProjection> findByUserId(@NonNull Long userId);
	
	List<UserIdProjection> findUserIdByUsernameIn(@NonNull List<String> usernames);
	
	Optional<UserEntity> findByConnectionInviteCode(@NonNull String connectionInviteCode);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<UserEntity> findForUpdateByUserId(@NonNull Long userId);
	
	Optional<InviteCodeProjection> findInviteCodeByUserId(@NonNull Long userId);
	
	Optional<CountProjection> findCountByUserId(@NonNull Long userId);
	
	
}
