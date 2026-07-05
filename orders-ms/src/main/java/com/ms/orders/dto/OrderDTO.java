package com.ms.orders.dto;

import java.util.List;

public record OrderDTO(String id, String customerId, List<ProductDTO> products, double total) {
    public OrderDTO {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer id cannot be null or empty");
        }
        products = List.copyOf(products);
        total = products.stream().mapToDouble(p -> p.price() * p.quantity()).sum();
    }
}
