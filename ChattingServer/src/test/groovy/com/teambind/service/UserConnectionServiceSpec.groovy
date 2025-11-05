package com.teambind.service

import com.teambind.auth.dto.InviteCode
import com.teambind.auth.dto.User
import com.teambind.auth.dto.projection.InvitorUserIdProjection
import com.teambind.auth.dto.projection.UserConnectionStatusProjection
import com.teambind.auth.entity.UserConnectionEntity
import com.teambind.auth.entity.UserEntity
import com.teambind.auth.entity.UserId
import com.teambind.auth.repository.UserConnectionRepository
import com.teambind.auth.repository.UserRepository
import com.teambind.chattingserver.service.UserConnectionLimitService
import com.teambind.chattingserver.service.UserConnectionService
import com.teambind.chattingserver.service.UserService
import com.teambind.constant.UserConnectionStatus
import org.springframework.data.util.Pair
import spock.lang.Specification

class UserConnectionServiceSpec extends Specification {
    UserConnectionService userConnectionService
    UserConnectionLimitService userConnectionLimitService;
    UserRepository userRepository = Stub();
    UserService userService = Stub()
    UserConnectionRepository userConnectionRepository = Stub()


    def setup() {
        userConnectionLimitService = new UserConnectionLimitService(userRepository, userConnectionRepository)
        userConnectionService = new UserConnectionService(userService, userConnectionLimitService, userConnectionRepository)
    }

    def "사용자 연결 신청에 대한 테스트."() {
        given:
        userService.getUser(inviteCodeOfTargetUser) >> Optional.of(new User(targetUserId, targetUsername))
        userService.getUsername(senderUserId) >> Optional.of(senderUsername)
        userConnectionRepository.findByPartnerUserAIdAndPartnerUserBId(_ as Long, _ as Long) >> {
            Optional.of(Stub(UserConnectionStatusProjection) {
                getStatus() >> beforeConnectionStatus.name()
            })
        }
        userService.getConnectionCount(senderUserId) >> { senderUserId.id() != 8 ? Optional.of(0) : Optional.of(1000) }


        when:
        def result = userConnectionService.invite(senderUserId, usedInviteCode)

        then:
        result == expectedResult


        where:
        scenario             | senderUserId  | senderUsername | targetUserId  | targetUsername | inviteCodeOfTargetUser      | usedInviteCode              | beforeConnectionStatus        | expectedResult
        'Valid invite code'  | new UserId(1) | 'userA'        | new UserId(2) | 'userB'        | new InviteCode('user2Code') | new InviteCode('user2Code') | UserConnectionStatus.NONE     | Pair.of(Optional.of(new UserId(2)), 'userA')
        'Already connected ' | new UserId(1) | 'userA'        | new UserId(2) | 'userB'        | new InviteCode('user2Code') | new InviteCode('user2Code') | UserConnectionStatus.ACCEPTED | Pair.of(Optional.of(new UserId(2)), "already accepted to " + targetUsername)
        'Already invited '   | new UserId(1) | 'userA'        | new UserId(2) | 'userB'        | new InviteCode('user2Code') | new InviteCode('user2Code') | UserConnectionStatus.PENDING  | Pair.of(Optional.of(new UserId(2)), "already invited to " + targetUsername)

    }

    def "사용자 연결 신청 수락에 대한 테스트."() {

        given:
        userService.getUserId(targetUsername) >> Optional.of(targetUserId)
        userService.getUsername(senderUserId) >> Optional.of(senderUsername)
        userRepository.findForUpdateByUserId(_ as Long) >> { Long userId ->
            def entity = new UserEntity()
            if (userId == 5 || userId == 7) {
                entity.setConnectionCount(1000);
            }

            return Optional.of(entity)
        }

        userConnectionRepository.findByPartnerUserAIdAndPartnerUserBIdAndStatus(_ as Long, _ as Long, _ as UserConnectionStatus) >> {
            inviterUserId.flatMap { UserId inviter -> Optional.of(new UserConnectionEntity(senderUserId.id(), targetUserId.id(), UserConnectionStatus.PENDING, inviter.id()))
            }
        }

        userConnectionRepository.findByPartnerUserAIdAndPartnerUserBId(_ as Long, _ as Long) >> {
            Optional.of(Stub(UserConnectionStatusProjection) {
                getStatus() >> beforeConnectionStatus.name()
            })
        }

        userConnectionRepository.findInvitorUserIdByPartnerUserAIdAndPartnerUserBId(_ as Long, _ as Long) >> {
            inviterUserId.flatMap { UserId invitor ->
                {
                    Optional.of(Stub(InvitorUserIdProjection) {
                        getInvitorUserId() >> invitor.id()
                    })
                }
            }
        }


        when:

        def result = userConnectionService.accept(senderUserId, targetUsername)

        then:
        result == expectedResult

        where:
        scenario              | senderUserId  | senderUsername | targetUserId  | targetUsername | inviterUserId              | beforeConnectionStatus        | expectedResult
        "Accept invite"       | new UserId(1) | 'userA'        | new UserId(2) | 'userB'        | Optional.of(new UserId(2)) | UserConnectionStatus.PENDING  | Pair.of(Optional.of(new UserId(2)), 'userA')
        "Already Connected"   | new UserId(1) | 'userA'        | new UserId(2) | 'userB'        | Optional.of(new UserId(2)) | UserConnectionStatus.ACCEPTED | Pair.of(Optional.empty(), "already accepted to " + targetUsername)
        "Self Accept"         | new UserId(1) | 'userA'        | new UserId(1) | 'userA'        | Optional.of(new UserId(1)) | UserConnectionStatus.PENDING  | Pair.of(Optional.empty(), "cannot accept self")
        "Accept wrong invite" | new UserId(1) | 'userA'        | new UserId(4) | 'userD'        | Optional.of(new UserId(2)) | UserConnectionStatus.PENDING  | Pair.of(Optional.empty(), 'invalid username')


    }
}
