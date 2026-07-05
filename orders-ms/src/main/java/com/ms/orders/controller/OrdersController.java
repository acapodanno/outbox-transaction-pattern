package com.ms.orders.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ms.orders.dto.OrderDTO;
import com.ms.orders.service.OrdersService;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders API", description = "Endpoint for managing orders in the system")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    @Operation(summary = "Create a new Order", description = "Accepts a list of products and a customer ID, calculates the total, and saves the order to Cosmos DB.")
    @ApiResponse(responseCode = "201", description = "Order created successfully. Returns the UUID of the created order.")
    @ApiResponse(responseCode = "400", description = "Validation failed for order fields (e.g. empty products or empty customer ID).")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        String orderId = ordersService.createOrder(orderDTO);
        return ResponseEntity.created(URI.create("/orders/" + orderId)).body(orderId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID and Customer ID (Partition Key)", description = "Retrieves a single order using point-read by passing both the ID and the partition key (Customer ID).")
    @ApiResponse(responseCode = "200", description = "Order found.")
    @ApiResponse(responseCode = "404", description = "Order not found.")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id, @RequestParam String customerId) {
        OrderDTO orderDTO = ordersService.getOrderById(id, customerId);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get all orders for a customer", description = "Retrieves all orders belonging to a specific customer using single-partition query.")
    @ApiResponse(responseCode = "200", description = "List of orders (can be empty).")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable String customerId) {
        List<OrderDTO> orders = ordersService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

}
