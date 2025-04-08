package com.tregubov.firstserver.service;

import com.tregubov.firstserver.DTOs.AuthRequestDTO;
import com.tregubov.firstserver.constants.Errors;
import com.tregubov.firstserver.constants.Success;
import com.tregubov.firstserver.entities.Account;
import com.tregubov.firstserver.repository.AccountRepository;
import com.tregubov.firstserver.securiry.PasswordEncryptor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private final AccountRepository accountRepository;

    public AuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public int register(AuthRequestDTO authRequestDTO) {
        if (accountRepository.existsByEmail(authRequestDTO.getEmail())) {
            return Errors.ACCOUNT_ALREADY_EXISTS;
        }

        try {

            Account newAccount = new Account();
            newAccount.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            newAccount.setEmail(authRequestDTO.getEmail());
            newAccount.setPassword(PasswordEncryptor.hashPassword(authRequestDTO.getPassword()));
            accountRepository.save(newAccount);
            return Success.ACCOUNT_IS_REGISTERED;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return Errors.ACCOUNT_REGISTRATION_FAILED;
        }
    }

    public int login(AuthRequestDTO authRequestDTO) {
        Optional<Account> accountContainer = accountRepository.findByEmail(authRequestDTO.getEmail());

        if (accountContainer.isEmpty()) {
            return Errors.ACCOUNT_NOT_EXISTS;
        }

        try {

            if (!PasswordEncryptor.checkPassword(authRequestDTO.getPassword(), accountContainer.get().getPassword())) {
                return Errors.INCORRECT_PASSWORD;
            }
            return Success.ACCOUNT_IS_LOGGED_IN;

        } catch (Exception exception) {
            return Errors.ACCOUNT_LOGGING_FAILED;
        }
    }

}
