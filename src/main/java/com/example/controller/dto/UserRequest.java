package com.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest{
    private String name;
    @JsonProperty(value="email", required = true)
    private String email;
    @JsonProperty(value="password", required = true)
    private String password;
    private List<Phone> phones;
}
