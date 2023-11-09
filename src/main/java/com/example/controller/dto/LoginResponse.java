package com.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private String id;
    private Date created;
    private Date lastLogin;
    private String token;
    private boolean isActive;
}
