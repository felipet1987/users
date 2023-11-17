package com.example.repository;

import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.Phone;
import com.example.controller.dto.UserRequest;
import com.example.port.IUserDataRepository;
import com.example.repository.dao.PhoneDao;
import com.example.repository.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public  class UserDataRepository implements IUserDataRepository{
    private PhoneRepository phoneRepository;
    private UserRepository userRepository;


    private static LoginResponse mapToResponse(UserDao user, List<PhoneDao> userPhones) {
        List<Phone> phones = userPhones.stream()
                .map(p -> Phone.builder()
                        .number(p.getNumber())
                        .countryCode(p.getCountryCode())
                        .cityCode(p.getCityCode())
                        .build())
                .collect(Collectors.toList());

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastLogin(user.getLastLogin())
                .name(user.getName())
                .password(user.getPassword())
                .isActive(user.isActive())
                .created(user.getCreated())
                .phones(phones)
                .build();
    }

    public UserDao save(UserRequest request) {
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
                .build());
        log.info("finish  save user");


        if (Optional.ofNullable(request.getPhones()).isPresent()) {
            request.getPhones().forEach(p -> {
                log.info("init  save phones");
                phoneRepository.save(PhoneDao.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(saved.getId())
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

    public LoginResponse getUser(String id) {


        List<PhoneDao> phones = (List<PhoneDao>) phoneRepository.findAll();

        Optional<UserDao> OptionalUser = userRepository.findById(id);


        if (OptionalUser.isPresent()) {
            UserDao user = OptionalUser.get();
            user.setLastLogin(new Date());
            userRepository.save(user);
            List<PhoneDao> userPhones = phones.stream()
                    .filter(p -> Objects.equals(p.getUserId(), id))
                    .collect(Collectors.toList());


            return mapToResponse(user, userPhones);
        }
        throw new RuntimeException();

    }


}
