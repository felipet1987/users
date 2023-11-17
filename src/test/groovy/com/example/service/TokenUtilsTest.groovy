package com.example.service

import com.example.security.TokenUtils
import spock.lang.Specification

class TokenUtilsTest extends Specification {


    def "validate"() {
        String token = TokenUtils.getJWTToken("name", "1234")
        String id = TokenUtils.validateToken(token)

        expect:
        id == "1234"
    }
}
