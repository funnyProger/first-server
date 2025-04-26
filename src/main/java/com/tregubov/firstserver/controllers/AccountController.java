package com.tregubov.firstserver.controllers;

import com.tregubov.firstserver.DTOs.AccountIdDTO;
import com.tregubov.firstserver.DTOs.OrderDTO;
import com.tregubov.firstserver.DTOs.UpdateCartOrFavoritesDTO;
import com.tregubov.firstserver.constants.Errors;
import com.tregubov.firstserver.entities.order.AccountOrder;
import com.tregubov.firstserver.entities.product.Comment;
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
        ResponseEntity<String> response = ResponseEntity.ok("Успешно добавлено");

        if (!result) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте добавить продукт в корзину позже");
        }

        return response;
    }

    @PostMapping("/remove-from-favorites")
    public ResponseEntity<String> removeFromFavorites(@RequestBody UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        boolean result = accountService.removeProductFromFavorites(updateCartOrFavoritesDTO);
        ResponseEntity<String> response = ResponseEntity.ok("Успешно убрано");

        if (!result) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте убрать продукт из избранного позже");
        }

        return response;
    }

    @PostMapping("/favorites")
    public Set<Product> getFavorites(@RequestBody AccountIdDTO accountIdDTO) {
        return accountService.getFavoriteProducts(accountIdDTO);
    }

    @PostMapping("/orders")
    public Set<AccountOrder> getOrders(@RequestBody AccountIdDTO accountIdDTO) {
        return accountService.getAccountOrders(accountIdDTO);
    }

    @PostMapping("/comments")
    public Set<Comment> getComments(@RequestBody AccountIdDTO accountIdDTO) {
        return accountService.getComments(accountIdDTO);
    }

    @PostMapping("/order")
    public ResponseEntity<String> order(@RequestBody OrderDTO orderDTO) {
        int result = accountService.makeAnOrder(orderDTO);
        ResponseEntity<String> response = ResponseEntity.ok("Заказ совершен");

        if (result == Errors.PRODUCT_NOT_EXISTS) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт не найден");
        }
        if (result == Errors.PRODUCT_NOT_AVAILABLE) {
            response = ResponseEntity.status(HttpStatus.GONE).body("Продукт закончился");
        }
        if (result == Errors.ACCOUNT_NOT_EXISTS) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        if (result == Errors.ORDER_STATUS_NOT_EXISTS) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера, попробуйте войти позже");
        }

        return response;
    }


}
