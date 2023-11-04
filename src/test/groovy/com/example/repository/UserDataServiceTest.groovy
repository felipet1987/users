package com.example.repository

import com.example.controller.dto.Phone
import com.example.controller.dto.UserRequest
import com.example.repository.dao.UserDao
import spock.lang.Specification

class UserDataServiceTest extends Specification {
    PhoneRepository phoneRepository
    UserDataService service
    UserRepository userRepository

    void setup() {
        phoneRepository = Stub()
        userRepository = Mock()
        service = new UserDataService(phoneRepository, userRepository)
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

        UserDao saved = service.save(request, "token")

        expect:
        saved.getEmail() == "email"
        saved.getName() == "name"

    }
}
