package com.example.controller

import com.example.controller.dto.HttpError
import com.example.controller.dto.UserRequest
import com.example.controller.dto.UserResponse
import com.example.controller.exception.EmailException
import com.example.controller.exception.PasswordException
import com.example.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class UserRequestControllerTest extends Specification {

    UserService mockService
    UserController controller
    Validator validator;

    def setup() {
        validator = Stub()
        mockService = Stub()
        controller = new UserController(mockService, validator)
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
        validator.validatePassword("password") >> true
        validator.validateEmail("email") >> true

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
        validator.validatePassword("password") >> true
        validator.validateEmail("email") >> true
        mockService.process(request) >> Optional.empty()

        when:
        controller.signup(request);

        then:
        thrown(ResponseStatusException);
    }
    def "Signup validate password false"() {
        given:
        UserRequest request = UserRequest.builder()
                .email("email")
                .password("password").build()
        validator.validatePassword("password") >> false
        validator.validateEmail("email") >> true

        when:
        controller.signup(request);

        then:
        thrown(PasswordException)
    }

    def "handle exceptions"() {
        ResponseEntity<HttpError> error = controller.handleException(new RuntimeException())
        expect:
        error.body.codigo == 500
    }

    def "handle null"() {
        ResponseEntity<HttpError> error = controller.handleNullError(new NullPointerException())
        expect:
        error.body.codigo == 500
    }

    def "handle null"() {
        ResponseEntity<HttpError> error = controller.handleNotFoundError(new ResponseStatusException(HttpStatus.NOT_FOUND))
        expect:
        error.body.codigo == 400
    }

    def "Signup validate email false"() {
        given:
        UserRequest request = UserRequest.builder()
                .email("email")
                .password("password").build()
        validator.validatePassword("password") >> true
        validator.validateEmail("email") >> false

        when:
        controller.signup(request);

        then:
        thrown(EmailException)
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
