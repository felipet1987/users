package com.example.service


import com.example.controller.dto.UserRequest
import com.example.controller.dto.UserResponse
import com.example.repository.UserDataService
import com.example.repository.dao.UserDao
import spock.lang.Specification

class UserServiceTest extends Specification {
    UserDataService userDataService
    UserService service
    TokenService tokenService

    def setup() {
        userDataService = Stub()
        tokenService = Stub()
        service = new UserService(userDataService)
    }

    def "process"() {
        given:
        def today = new Date()
        UserRequest request = UserRequest.builder()
        .name("name")
                .build()


        def saved = UserDao.builder()
                .token("token")
                .id("id")
                .isActive(true)
                .created(today)
                .lastLogin(today)
                .build()
        userDataService.save(request) >> saved


        when:
        Optional<UserResponse> response = service.process(request)


        then:
        response == Optional.of(UserResponse.builder()
                .id("id")
                .token("token")
                .isActive(true)
                .lastLogin(today)
                .created(today)
                .build())
    }

    def "process fail"() {
        given:
        UserRequest request = UserRequest.builder()
        .name("name")
                .build()

        userDataService.save(request) >> null


        when:
        service.process(request)


        then:
        thrown RuntimeException

    }

}
