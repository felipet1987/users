package com.example.repository

import com.example.controller.dto.LoginResponse
import com.example.controller.dto.Phone
import com.example.controller.dto.UserRequest
import com.example.repository.dao.UserDao
import com.example.security.TokenUtils
import spock.lang.Specification

class UserDataRepositoryTest extends Specification {
    PhoneRepository phoneRepository
    UserDataRepository service
    UserRepository userRepository


    void setup() {
        phoneRepository = Stub()
        userRepository = Stub()
        service = new UserDataRepository(phoneRepository, userRepository)
    }

    def "get user"() {



        def user = UserDao.builder()
                .id("1234")
        .name("name")
                .build()
        userRepository.findById("1234") >> Optional.of(user)


        LoginResponse response = service.getUser('1234');



        def expected = LoginResponse.builder()
                .id("1234")
                .phones(new ArrayList<Phone>())
                .build()
        expect:
        response.getId() == '1234'
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
