package com.example.controller;

import com.example.controller.dto.HttpError;
import com.example.controller.dto.LoginResponse;
import com.example.controller.dto.UserRequest;
import com.example.controller.dto.UserResponse;
import com.example.controller.exception.EmailException;
import com.example.controller.exception.PasswordException;
import com.example.port.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;


@RestController()
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final String PREFIX = "Bearer ";
    private IUserService service;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes="application/json")
    public UserResponse signup(@RequestBody UserRequest request) {


        Optional<UserResponse> process = service.process(request);

        if (process.isPresent()) {
            return process.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes="application/json")
    public LoginResponse login(@RequestHeader("Authorization") String token) {
        log.info(token);
        String jwtToken = token.replace(PREFIX, "");
        return service.login(jwtToken);
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
    public ResponseEntity<HttpError> handleError(Exception e) {

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
    public ResponseEntity<HttpError> handleNullError(NullPointerException e) {

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
    public ResponseEntity<HttpError> handleNotFoundError(Exception e) {


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(HttpError.builder()
                        .codigo(400)
                        .detail(e.getMessage())
                        .timestamo(Instant.now())
                        .build());
    }
}
