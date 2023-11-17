package com.example.controller

import com.example.service.Validator
import spock.lang.Specification

class ValidatorTest extends Specification {
    def "validate Email"() {
        Validator validator = new Validator()
        boolean  response = validator.validateEmail("a2asfGfdfdf4@dominio.algo")

        expect:
        response
    }

    def "validate Email false"() {
        Validator validator = new Validator()
        boolean  response = validator.validateEmail("a2asfGfd")

        expect:
        !response
    }
    def "validate Password"() {
        Validator validator = new Validator()
        boolean  response = validator.validatePassword("a2asfGfdfdf4")

        expect:
        response
    }

    def "validate Password false"() {
        Validator validator = new Validator()
        boolean  response = validator.validateEmail("sdfsddfg")

        expect:
        !response
    }
}
