package com.example.port;

import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.UserRequest;
import com.example.repository.dao.UserDao;

public interface IUserDataRepository {
    UserDao save(UserRequest request);

    LoginResponse getUser(String id);
}
