package com.example.controller

import com.example.controller.dto.HttpError
import com.example.controller.dto.LoginResponse
import com.example.controller.dto.UserRequest
import com.example.controller.dto.UserResponse
import com.example.controller.exception.EmailException
import com.example.controller.exception.PasswordException
import com.example.service.UserService
import com.example.service.Validator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class UserRequestControllerTest extends Specification {

    UserService mockService
    UserController controller

    def setup() {
        mockService = Stub()
        controller = new UserController(mockService)
    }

    def "Signup"() {
        given:
        UserRequest request = UserRequest.builder()
                .password("password")
                .email("email")
                .build()

        UserResponse response = UserResponse.builder()
                .id("id")
                .build()
        mockService.process(request) >> Optional.of(response)

        when:
        UserResponse actual = controller.signup(request);

        then:
        actual == response

    }

    def "Signup fail"() {
        given:
        UserRequest request = UserRequest.builder()
                .password("password")
                .email("email")
                .build()
        mockService.process(request) >> Optional.empty()

        when:
        controller.signup(request);

        then:
        thrown(ResponseStatusException);
    }


    def "handle exceptions"() {
        ResponseEntity<HttpError> error = controller.handleException(new RuntimeException())
        expect:
        error.body.codigo == 500
    }

    def "handle 500"() {
        ResponseEntity<HttpError> error = controller.handleNullError(new NullPointerException())
        expect:
        error.body.codigo == 500
    }
    def "handle error"() {
        ResponseEntity<HttpError> error = controller.handleError(new NullPointerException())
        expect:
        error.body.codigo == 500
    }

    def "handle 400"() {
        ResponseEntity<HttpError> error = controller.handleNotFoundError(new ResponseStatusException(HttpStatus.NOT_FOUND))
        expect:
        error.body.codigo == 400
    }

    def "handle login"() {

        mockService.login("token") >> LoginResponse.builder()
                .token("token")
                .build()

        LoginResponse response
        response = controller.login("token")

        expect:
        response == LoginResponse.builder()
                .token("token")
                .build()

    }



    def "Signup request mull"() {
        given:
        UserRequest request = null


        when:
        controller.signup(request);

        then:
        thrown(ResponseStatusException)
    }
    def "Signup request password mull"() {
        given:
        UserRequest request = UserRequest.builder().build()
        when:
        controller.signup(request);

        then:
        thrown(ResponseStatusException)
    }
    def "Signup request email mull"() {
        given:
        UserRequest request = UserRequest.builder().password("").build()
        when:
        controller.signup(request);

        then:
        thrown(ResponseStatusException)
    }
}
