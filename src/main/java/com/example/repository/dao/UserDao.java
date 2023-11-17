package com.example.repository.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDao {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Date created;
    private Date lastLogin;
    private boolean isActive;

}
