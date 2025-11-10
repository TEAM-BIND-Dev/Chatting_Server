package com.teambind.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.teambind.chattingserver.dto.websocket.inbound.*
import com.teambind.util.JsonUtil
import spock.lang.Specification

class RequestTypeMappingSpec extends Specification {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonUtil jsonUtil = new JsonUtil(objectMapper);


    def "DTO 형식의 JSON 문자열을 해당 타입의 DTO로 변환할 수 있다."() {
        given:
        String jsonBody = payload

        when:
        BaseRequest request = jsonUtil.fromJson(jsonBody, BaseRequest).get()

        then:
        request.getClass() == expectedClass
        validate(request)


        where:
        payload                                                                    | expectedClass       | validate
        '{"type": "INVITE_REQUEST", "userInviteCode":"TestInviteCode123"}'         | InviteRequest       | { req -> (req as InviteRequest).userInviteCode.code() == 'TestInviteCode123' }
        '{"type": "ACCEPT_REQUEST", "username":"testuser"}'                        | AcceptRequest       | { req -> (req as AcceptRequest).username == 'testuser' }
        '{"type": "WRITE_MESSAGE", "username":"testuser","content":"testMessage"}' | WriteMessage | { req -> (req as WriteMessage).getContent() == "testMessage" }
        '{"type": "KEEP_ALIVE"}'                                                   | KeepAlive | { req -> (req as KeepAlive).getType() == 'KEEP_ALIVE' }

    }
}
