package com.tregubov.firstserver.service;

import com.tregubov.firstserver.DTOs.LoginRequest;
import com.tregubov.firstserver.DTOs.RegisterRequest;
import com.tregubov.firstserver.constants.Errors;
import com.tregubov.firstserver.constants.Success;
import com.tregubov.firstserver.entities.Account;
import com.tregubov.firstserver.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public int register(RegisterRequest registerRequest) {
        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            return Errors.ACCOUNT_ALREADY_EXISTS;
        }

        try {

            Account newAccount = new Account();
            newAccount.setEmail(registerRequest.getEmail());
            newAccount.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            accountRepository.save(newAccount);
            return Success.ACCOUNT_IS_REGISTERED;

        } catch (Exception exception) {
            return Errors.ACCOUNT_REGISTRATION_FAILED;
        }
    }

    public int login(LoginRequest loginRequest) {
        Optional<Account> accountContainer = accountRepository.findByEmail(loginRequest.getEmail());

        if (accountContainer.isEmpty()) {
            return Errors.ACCOUNT_NOT_EXISTS;
        }

        try {

            if (!passwordEncoder.matches(loginRequest.getPassword(), accountContainer.get().getPassword())) {
                return Errors.INCORRECT_PASSWORD;
            }
            return Success.ACCOUNT_IS_LOGGED_IN;

        } catch (Exception exception) {
            return Errors.ACCOUNT_LOGGING_FAILED;
        }
    }

}
