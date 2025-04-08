package com.tregubov.firstserver.service;

import com.tregubov.firstserver.DTOs.AccountIdDTO;
import com.tregubov.firstserver.DTOs.UpdateCartOrFavoritesDTO;
import com.tregubov.firstserver.entities.Account;
import com.tregubov.firstserver.entities.Product;
import com.tregubov.firstserver.repository.AccountRepository;
import com.tregubov.firstserver.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    public AccountService(AccountRepository accountRepository, ProductRepository productRepository) {
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    public boolean addProductToCart(UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        try {
            Optional<Account> accountContainer = accountRepository.findById(updateCartOrFavoritesDTO.getAccountId());
            Optional<Product> productContainer = productRepository.findById(updateCartOrFavoritesDTO.getProductId());

            if (accountContainer.isEmpty() || productContainer.isEmpty()) {
                return false;
            }

            Account account = accountContainer.get();
            Product product = productContainer.get();

            account.getCart().add(product);
            accountRepository.save(account);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean removeProductFromCart(UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        try {
            Optional<Account> accountContainer = accountRepository.findById(updateCartOrFavoritesDTO.getAccountId());
            Optional<Product> productContainer = productRepository.findById(updateCartOrFavoritesDTO.getProductId());

            if (accountContainer.isEmpty() || productContainer.isEmpty()) {
                return false;
            }

            Account account = accountContainer.get();
            Product product = productContainer.get();

            account.getCart().remove(product);
            accountRepository.save(account);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Set<Product> getCartProducts(AccountIdDTO accountIdDTO) {
        Optional<Account> accountContainer = accountRepository.findById(accountIdDTO.getAccountId());
        Set<Product> cart = new HashSet<>();

        if (accountContainer.isPresent()) {
            cart = accountContainer.get().getCart();
        }

        return cart;
    }

    public boolean addProductToFavorites(UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        try {
            Optional<Account> accountContainer = accountRepository.findById(updateCartOrFavoritesDTO.getAccountId());
            Optional<Product> productContainer = productRepository.findById(updateCartOrFavoritesDTO.getProductId());

            if (accountContainer.isEmpty() || productContainer.isEmpty()) {
                return false;
            }

            Account account = accountContainer.get();
            Product product = productContainer.get();

            account.getFavorites().add(product);
            accountRepository.save(account);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean removeProductFromFavorites(UpdateCartOrFavoritesDTO updateCartOrFavoritesDTO) {
        try {
            Optional<Account> accountContainer = accountRepository.findById(updateCartOrFavoritesDTO.getAccountId());
            Optional<Product> productContainer = productRepository.findById(updateCartOrFavoritesDTO.getProductId());

            if (accountContainer.isEmpty() || productContainer.isEmpty()) {
                return false;
            }

            Account account = accountContainer.get();
            Product product = productContainer.get();

            account.getFavorites().remove(product);
            accountRepository.save(account);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Set<Product> getFavoriteProducts(AccountIdDTO accountIdDTO) {
        Optional<Account> accountContainer = accountRepository.findById(accountIdDTO.getAccountId());
        Set<Product> favorites = new HashSet<>();

        if (accountContainer.isPresent()) {
            favorites = accountContainer.get().getFavorites();
        }

        return favorites;
    }
}
