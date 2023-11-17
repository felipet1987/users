package com.example.service;

import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.UserRequest;
import com.example.controller.dto.UserResponse;
import com.example.controller.exception.EmailException;
import com.example.controller.exception.PasswordException;
import com.example.port.IUserDataRepository;
import com.example.port.IUserService;
import com.example.repository.dao.UserDao;
import com.example.security.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public  class UserService implements IUserService{


    private IUserDataRepository userDataRepository;


    private static UserResponse map(UserDao saved) {

        String token = TokenUtils.getJWTToken(saved.getName(), saved.getId());
        return UserResponse.builder()
                .id(saved.getId())
                .token(token)
                .created(saved.getCreated())
                .lastLogin(saved.getLastLogin())
                .isActive(saved.isActive())
                .build();
    }

    public Optional<UserResponse> process(UserRequest request) {
        log.info("init process");

        if (request == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "password cannot be null");
        }

        if (request.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "password cannot be null");
        }
        if (request.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "email cannot be null");
        }


        if (!Validator.validateEmail(request.getEmail())) {
            throw new EmailException();
        }

        if (!Validator.validatePassword(request.getPassword())) {
            throw new PasswordException();
        }


        UserDao saved = userDataRepository.save(request);

        UserResponse mapped = map(saved);


        log.info("finish process");
        return Optional.ofNullable(mapped);
    }

    public LoginResponse login(String token) {
        log.info(token);

        String id = TokenUtils.validateToken(token);
        LoginResponse loginResponse = userDataRepository.getUser(id);
        String newToken = TokenUtils.getJWTToken(loginResponse.getName(),loginResponse.getId());
        loginResponse.setToken(newToken);
        return loginResponse;
    }


}
