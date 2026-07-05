package com.ms.orders.domain;

import java.util.List;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

@Container(containerName = "orders")
public class Order {
    @Id
    private String id;

    @PartitionKey
    private String customerId;

    private List<Product> products;
    private double total;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private OrderStatus status;

    public Order() {
    }

    public Order(String id, String customerId, List<Product> products, double total) {
        this.id = id;
        this.customerId = customerId;
        this.products = products;
        this.total = total;
        this.status = OrderStatus.PENDING;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
