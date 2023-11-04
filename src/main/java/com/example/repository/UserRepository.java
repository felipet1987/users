package com.example.repository;

import com.example.repository.dao.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDao, String> {
}
