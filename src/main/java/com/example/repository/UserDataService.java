package com.example.repository;

import com.example.controller.dto.UserRequest;
import com.example.repository.dao.PhoneDao;
import com.example.repository.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserDataService {
    PhoneRepository phoneRepository;
    UserRepository userRepository;

    public UserDao save(UserRequest request, String token) {
        log.info("init save");
        String id = UUID.randomUUID().toString();

        log.info("init  save user");
        UserDao saved = userRepository.save(UserDao.builder()
                .id(id)
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .lastLogin(new Date())
                .created(new Date())
                .isActive(true)
                .token(token)
                .build());
        log.info("finish  save user");



if(Optional.ofNullable(request.getPhones()).isPresent()){
    request.getPhones().forEach(p -> {
        log.info("init  save phones");
        phoneRepository.save(PhoneDao.builder()
                .id(UUID.randomUUID().toString())
                .cityCode(p.getCityCode())
                .countryCode(p.getCountryCode())
                .number(p.getNumber())
                .build());
        log.info("finish  save phones");
    });
}


        log.info("finish save");
        return saved;
    }

}
