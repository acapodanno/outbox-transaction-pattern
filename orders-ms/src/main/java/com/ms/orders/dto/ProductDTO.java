package com.ms.orders.dto;

public record ProductDTO(String name, int quantity, double price, String description) {
    public ProductDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Product quantity must be positive");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
    }
}
