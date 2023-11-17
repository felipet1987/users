package com.example

import com.example.controller.UserController
import com.example.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired (required = false)
    private UserController userController

    @Autowired (required = false)
    private UserService userService

    def "when context is loaded then all expected beans are created"() {
        expect:
        userController
        userService
    }
}
