package com.tregubov.firstserver.service;

import com.tregubov.firstserver.DTOs.*;
import com.tregubov.firstserver.constants.Errors;
import com.tregubov.firstserver.constants.Success;
import com.tregubov.firstserver.entities.account.Account;
import com.tregubov.firstserver.entities.order.AccountOrder;
import com.tregubov.firstserver.entities.order.OrderStatus;
import com.tregubov.firstserver.entities.order.Promocode;
import com.tregubov.firstserver.entities.product.Comment;
import com.tregubov.firstserver.entities.product.CommentImage;
import com.tregubov.firstserver.entities.product.CommentVideo;
import com.tregubov.firstserver.entities.product.Product;
import com.tregubov.firstserver.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final PromocodeRepository promocodeRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CommentRepository commentRepository;

    public AccountService(
            AccountRepository accountRepository,
            ProductRepository productRepository,
            PromocodeRepository promocodeRepository,
            OrderStatusRepository orderStatusRepository,
            CommentRepository commentRepository
    ) {
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.promocodeRepository = promocodeRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.commentRepository = commentRepository;
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

    public Set<AccountOrder> getAccountOrders(AccountIdDTO accountIdDTO) {
        Optional<Account> accountContainer = accountRepository.findById(accountIdDTO.getAccountId());
        Set<AccountOrder> accountOrders = new HashSet<>();

        if (accountContainer.isPresent()) {
            accountOrders = accountContainer.get().getOrders();
        }

        return accountOrders;
    }

    public Set<Comment> getComments(AccountIdDTO accountIdDTO) {
        Optional<Account> accountContainer = accountRepository.findById(accountIdDTO.getAccountId());
        Set<Comment> comments = new HashSet<>();

        if (accountContainer.isPresent()) {
            comments = accountContainer.get().getComments();
        }

        return comments;
    }

    public boolean addComment(AddCommentDTO addCommentDTO) {
        try {
            Optional<Account> accountContainer = accountRepository.findById(addCommentDTO.getAccountId());
            Optional<Product> productContainer = productRepository.findById(addCommentDTO.getProductId());

            if (accountContainer.isPresent() && productContainer.isPresent()) {
                Comment comment = new Comment();
                comment.setCreatedAt(Timestamp.from(Instant.now()));
                comment.setProduct(productContainer.get());
                comment.setAccount(accountContainer.get());
                comment.setRating(addCommentDTO.getRating());
                comment.setAdvantage(addCommentDTO.getAdvantage());
                comment.setDisadvantage(addCommentDTO.getDisadvantage());
                Set<CommentImage> commentImages = comment.getImages();
                Set<CommentVideo> commentVideos = comment.getVideos();

                if (commentImages != null && !commentImages.isEmpty()) {
                    for (String image: addCommentDTO.getImages()) {
                        CommentImage commentImage = new CommentImage();
                        commentImage.setComment(comment);
                        commentImage.setImage(Base64.getDecoder().decode(image));
                        commentImages.add(commentImage);
                    }
                }
                if (commentVideos != null && !commentVideos.isEmpty()) {
                    for (String video: addCommentDTO.getVideos()) {
                        CommentVideo commentVideo = new CommentVideo();
                        commentVideo.setComment(comment);
                        commentVideo.setVideo(Base64.getDecoder().decode(video));
                        commentVideos.add(commentVideo);
                    }
                }

                accountRepository.save(accountContainer.get());
                productRepository.save(productContainer.get());
                commentRepository.save(comment);

                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean removeComment(RemoveCommentDTO removeCommentDTO) {
        try {
            if (commentRepository.existsById(removeCommentDTO.getCommentId())) {
                commentRepository.deleteCommentById(removeCommentDTO.getCommentId());
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            return false;
        }
    }

    public int makeAnOrder(OrderDTO orderDTO) {
        Optional<Product> productContainer = productRepository.findById(orderDTO.getProductId());
        if (productContainer.isEmpty()) {
            return Errors.PRODUCT_NOT_EXISTS;
        }
        Product product = productContainer.get();
        if (product.getCount() == 0) {
            return Errors.PRODUCT_NOT_AVAILABLE;
        }

        Optional<Account> accountContainer = accountRepository.findById(orderDTO.getAccountId());
        if (accountContainer.isPresent()) {
            Optional<OrderStatus> orderStatusContainer = orderStatusRepository.findById(1);
            if (orderStatusContainer.isPresent()) {
                OrderStatus orderStatus = orderStatusContainer.get();
                Account account = accountContainer.get();
                AccountOrder accountOrder = new AccountOrder();
                double price;

                if (product.getDiscountProduct() != null) {
                    price = product.getDiscountProduct().getDiscountPrice().doubleValue();
                } else {
                    price = product.getPrice().doubleValue();
                }

                if (isValidPromocode(orderDTO)) {
                    Optional<Promocode> promocodeContainer = promocodeRepository.findById(orderDTO.getPromocodeId());
                    if (promocodeContainer.isPresent()) {
                        Promocode promocode = promocodeContainer.get();
                        accountOrder.setPromocode(promocode.getCode());
                        price = price - (price * (double) (promocode.getDiscount() / 100));
                    }
                }

                // здесь будет выполняться оплата заказа

                accountOrder.setAccount(account);
                accountOrder.setProduct(product);
                accountOrder.setPaid(true);
                accountOrder.setPrice(BigDecimal.valueOf(price));
                accountOrder.setStatus(orderStatus);

                account.getOrders().add(accountOrder);
                accountRepository.save(account);

                return Success.ORDER_PLACED;
            } else {
                return Errors.ORDER_STATUS_NOT_EXISTS;
            }
        } else {
            return Errors.ACCOUNT_NOT_EXISTS;
        }
    }

    boolean isValidPromocode(OrderDTO orderDTO) {
        Optional<Promocode> promocodeContainer = promocodeRepository.findById(orderDTO.getPromocodeId());
        if (promocodeContainer.isEmpty()) {
            return false;
        }

        Promocode promocode = promocodeContainer.get();
        long currentTime = System.currentTimeMillis();
        if (
                !(promocode.isActive()
                        && currentTime >= promocode.getStartDate().getTime()
                        && currentTime <= promocode.getEndDate().getTime())
        ) {
            return false;
        }

        return !promocodeRepository.isPromocodeUsedByAccount(orderDTO.getAccountId(), orderDTO.getPromocodeId());
    }
}
