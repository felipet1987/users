package com.example.service;

import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.UserRequest;
import com.example.controller.dto.UserResponse;
import com.example.repository.UserDataService;
import com.example.repository.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {


    private UserDataService userDataService;


    private static UserResponse map(UserDao saved) {
        return UserResponse.builder()
                .id(saved.getId())
                .token(saved.getToken())
                .created(saved.getCreated())
                .lastLogin(saved.getLastLogin())
                .isActive(saved.isActive())
                .build();
    }

    public Optional<UserResponse> process(UserRequest request) {
        log.info("init process");

        UserDao saved = userDataService.save(request);

        UserResponse mapped = map(saved);

        log.info("finish process");
        return Optional.ofNullable(mapped);
    }

    public LoginResponse login(String token) {
        return userDataService.getUser(token);
    }
}
