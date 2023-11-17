package com.example.port;

import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.UserRequest;
import com.example.controller.dto.UserResponse;

import java.util.Optional;

public interface IUserService {
    Optional<UserResponse> process(UserRequest request);

    LoginResponse login(String token);
}
