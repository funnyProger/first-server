package com.tregubov.firstserver.controllers;


import com.tregubov.firstserver.entities.Product;
import com.tregubov.firstserver.service.ProductService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/by-category/{id}")
    public List<Product> getProductsByCategory(@PathVariable("id") int categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

}
