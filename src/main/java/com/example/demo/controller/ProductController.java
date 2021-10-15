package com.example.demo.controller;
;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@AllArgsConstructor
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product result = service.createProduct(product);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
        } catch (BadRequestException e) {
            LOGGER.error("create a new product error: product id={} exists" + product.getId());
        } catch (IOException e) {
            LOGGER.error("create a new product error: storing data IO exception");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        try {
            Product result = service.updateProduct(id, product);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (BadRequestException e) {
            LOGGER.error("update product error: product id={} does not exist" + product.getId());
        } catch (IOException e) {
            LOGGER.error("update product error: updating data IO exception");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        try {
            Product result = service.getProductById(id);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (BadRequestException e) {
            LOGGER.error("get a product error: product id={} does not exist" + id);
        } catch (IOException e) {
            LOGGER.error("get a product error: fetching data IO exception");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "category", required = false) String category) {
        try {
            List<Product> result;
            if (null != category) {
                result = service.getProductByCategory(category);
            } else {
                result = service.getAllProducts();
            }

            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (BadRequestException e) {
            LOGGER.error("get products error: products don't not exist");
        } catch (IOException e) {
            LOGGER.error("get products error: fetching data IO exception");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
