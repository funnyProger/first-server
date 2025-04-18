package com.tregubov.firstserver.controllers;

import com.tregubov.firstserver.DTOs.AccountIdDTO;
import com.tregubov.firstserver.DTOs.UpdateCartOrFavoritesDTO;
import com.tregubov.firstserver.entities.product.Product;
import com.tregubov.firstserver.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestBody UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        boolean result = accountService.addProductToCart(updateCartOrFavoritesDTO);
        ResponseEntity<String> response = ResponseEntity.ok("Успешно добавлено");

        if (!result) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте добавить продукт в корзину позже");
        }

        return response;
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestBody UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        boolean result = accountService.removeProductFromCart(updateCartOrFavoritesDTO);
        ResponseEntity<String> response = ResponseEntity.ok("Успешно убрано");

        if (!result) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте убрать продукт из корзины позже");
        }

        return response;
    }

    @PostMapping("/cart")
    public Set<Product> getCart(@RequestBody AccountIdDTO accountIdDTO) {
        return accountService.getCartProducts(accountIdDTO);
    }

    @PostMapping("/add-to-favorites")
    public ResponseEntity<String> addToFavorites(@RequestBody UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        boolean result = accountService.addProductToFavorites(updateCartOrFavoritesDTO);
        ResponseEntity<String> respons = ResponseEntity.ok("Успешно добавлено");

        if (!result) {
            respons = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте добавить продукт в корзину позже");
        }

        return respons;
    }

    @PostMapping("/remove-from-favorites")
    public ResponseEntity<String> removeFromFavorites(@RequestBody UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        boolean result = accountService.removeProductFromFavorites(updateCartOrFavoritesDTO);
        ResponseEntity<String> respons = ResponseEntity.ok("Успешно убрано");

        if (!result) {
            respons = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте убрать продукт из избранного позже");
        }

        return respons;
    }

    @PostMapping("/favorites")
    public Set<Product> getFavorites(@RequestBody AccountIdDTO accountIdDTO) {
        return accountService.getFavoriteProducts(accountIdDTO);
    }

}
