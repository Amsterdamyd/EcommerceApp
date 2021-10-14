package com.example.demo.model;

import lombok.Data;

@Data
public class Product {
    long id;
    String name;
    String category;
    double retail_price; // two decimal places
    double discounted_price; // two decimal places
    boolean availability;
}
