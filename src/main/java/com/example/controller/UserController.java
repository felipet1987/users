package com.example.controller;

import com.example.controller.dto.HttpError;
import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.UserRequest;
import com.example.controller.dto.UserResponse;
import com.example.controller.exception.EmailException;
import com.example.controller.exception.PasswordException;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController()
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {


    private UserService service;
    private Validator validator;

    @PostMapping()
    public UserResponse signup(@RequestBody UserRequest request) {

        if(request== null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"password cannot be null");
        }

        if(request.getPassword() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"password cannot be null");
        }
        if(request.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"email cannot be null");
        }


        if (!validator.validateEmail(request.getEmail())) {
            throw new EmailException();
        }

        if (!validator.validatePassword(request.getPassword())) {
            throw new PasswordException();
        }


        Optional<UserResponse> process = service.process(request);

        if(process.isPresent()){
            return process.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }
    @PostMapping()
    public LoginResponse login(@RequestHeader String token) {

        return service.login(token);
    }


    @ExceptionHandler({
            PasswordException.class,
            EmailException.class,
    })
    public ResponseEntity<HttpError> handleException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpError.builder()
                        .codigo(500)
                        .detail(e.getMessage())
                        .timestamo(Instant.now())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<HttpError>  handleError(Exception e) {

        log.info(e.getClass().getName());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpError.builder()
                        .codigo(500)
                        .detail(e.getMessage())
                        .timestamo(Instant.now())
                        .build());
    }
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<HttpError>  handleNullError(NullPointerException e) {

        log.info(e.getClass().getName());
        log.info(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpError.builder()
                        .codigo(500)
                        .detail("")
                        .timestamo(Instant.now())
                        .build());
    }
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<HttpError>  handleNotFoundError(Exception e) {


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(HttpError.builder()
                        .codigo(400)
                        .detail(e.getMessage())
                        .timestamo(Instant.now())
                        .build());
    }
}
