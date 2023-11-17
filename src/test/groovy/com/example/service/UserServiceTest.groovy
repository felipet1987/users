package com.example.service

import com.example.controller.dto.LoginResponse
import com.example.controller.dto.UserRequest
import com.example.controller.dto.UserResponse
import com.example.repository.UserDataRepository
import com.example.repository.dao.UserDao
import com.example.security.TokenUtils
import spock.lang.Specification

class UserServiceTest extends Specification {
    UserDataRepository userDataService
    UserService service

    def setup() {
        userDataService = Stub()
        service = new UserService(userDataService)
    }

    def "process"() {
        given:
        def today = new Date()

        def globalMock = GroovyMock(TokenUtils,global: true)
        def instanceMock = Mock(TokenUtils)

        UserRequest request = UserRequest.builder()
                .name("name")
                .password("a2asfGfdfdf4")
                .email("user@email.com")
                .name("")
                .build()



        def saved = UserDao.builder()
                .id("id")
                .name("name")
                .password("a2asfGfdfdf4")
                .email("user@email.com")
                .isActive(true)
                .created(today)
                .lastLogin(today)
                .build()
        userDataService.save(request) >> saved


        when:
        Optional<UserResponse> response = service.process(request)


        then:

        response.get().getId() == "id"
        response.get().isActive()
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

    def "login"() {
        LoginResponse loginResponse = service.login("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxZGY4MWQyNS0xNjlmLTRkZDQtYmNlNy05NTRiMWQ4NTVhYjUiLCJzdWIiOiIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzAwMjMwNjU2LCJleHAiOjE3MDAyMzEyNTZ9.KuyM3Ah3UT3yQKbPlWjJOzcA2Kq-jyroSqgdNZtXQKkKvCilTHT_h4M7dfy8ddG0y6CCnRYs-_3YDdRbn4H_Ug")

        expect:
            loginResponse
    }
}
