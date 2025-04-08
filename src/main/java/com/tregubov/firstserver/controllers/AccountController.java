package com.tregubov.firstserver.controllers;

import com.tregubov.firstserver.DTOs.RegisterRequest;
import com.tregubov.firstserver.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

}
