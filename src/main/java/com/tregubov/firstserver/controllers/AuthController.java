package com.tregubov.firstserver.controllers;

import com.tregubov.firstserver.DTOs.LoginRequest;
import com.tregubov.firstserver.DTOs.RegisterRequest;
import com.tregubov.firstserver.constants.Errors;
import com.tregubov.firstserver.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        int result = authService.register(registerRequest);
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.CREATED).body("Аккаунт успешно создан");

        if (result == Errors.ACCOUNT_ALREADY_EXISTS) {
            response = ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Пользователь с почтной " + registerRequest.getEmail() + " уже существует");
        }
        if (result == Errors.ACCOUNT_REGISTRATION_FAILED) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте зарегистрироваться позже");
        }

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        int result = authService.login(loginRequest);
        ResponseEntity<String> response = ResponseEntity.ok("Вход успешно выполнен");

        if (result == Errors.ACCOUNT_NOT_EXISTS) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с указанной почтой не найден");
        }
        if (result == Errors.INCORRECT_PASSWORD) {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неправильный пароль, попробуйте другой");
        }
        if (result == Errors.ACCOUNT_LOGGING_FAILED) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте войти позже");
        }

        return response;
    }

}
