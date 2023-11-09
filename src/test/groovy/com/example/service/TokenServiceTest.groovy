package com.example.service

import spock.lang.Specification

class TokenServiceTest extends Specification {
    def "Generate"() {
        TokenService service = new TokenService()
        String token = service.generate("1234")

        expect:
        token
    }
    def "validate"() {
        TokenService service = new TokenService()
        String token = service.generate("1234")
        String id = service.validate(token)

        expect:
        id == "1234"
    }
}
