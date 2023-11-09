package com.example.repository

import com.example.controller.dto.LoginResponse
import com.example.controller.dto.Phone
import com.example.controller.dto.UserRequest
import com.example.repository.dao.UserDao
import com.example.service.TokenService
import spock.lang.Specification

class UserDataServiceTest extends Specification {
    PhoneRepository phoneRepository
    UserDataService service
    UserRepository userRepository
    TokenService tokenService

    void setup() {
        phoneRepository = Stub()
        userRepository = Stub()
        tokenService = new TokenService()
        service = new UserDataService(phoneRepository, userRepository, tokenService)
    }

    def "get user"() {

        String token = tokenService.generate("1234")


        def user = UserDao.builder()
                .id("1234")
                .build()
        userRepository.findById("1234") >> Optional.of(user)

        LoginResponse response = service.getUser(token);



        def expected = LoginResponse.builder()
                .id("1234")
                .phones(new ArrayList<Phone>())
                .build()
        expect:
        response == expected
    }

    def "Save"() {


        UserRequest request = UserRequest.builder()
                .phones(List.of(Phone.builder().cityCode(56)
                        .build()))
                .build()


        userRepository.save(_) >> UserDao.builder()
                .email("email")
                .name("name")
                .build()

        UserDao saved = service.save(request)

        expect:
        saved.getEmail() == "email"
        saved.getName() == "name"

    }
}
