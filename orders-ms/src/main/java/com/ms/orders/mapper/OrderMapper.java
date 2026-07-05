package com.ms.orders.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;

import com.ms.orders.domain.Order;
import com.ms.orders.domain.Product;
import com.ms.orders.dto.OrderDTO;
import com.ms.orders.dto.ProductDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Order toEntity(OrderDTO dto);

    OrderDTO toDTO(Order entity);

    Product toEntity(ProductDTO dto);

    ProductDTO toDTO(Product entity);
}
