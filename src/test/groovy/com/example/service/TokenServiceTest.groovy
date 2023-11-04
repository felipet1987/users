package com.example.service

import spock.lang.Specification

class TokenServiceTest extends Specification {
    def "Generate"() {
        TokenService service = new TokenService()
        String token = service.generate()

        expect:
        token
    }
}
