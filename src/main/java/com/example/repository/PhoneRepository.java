package com.example.repository;

import com.example.repository.dao.PhoneDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneDao,String> {
}
