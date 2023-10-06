package com.bouzourine.gestiondesproduits.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductResponseDto {
    Long id;
    String code;
    String name;
    String description;
    int  price;
    int quantity;
    String inventoryStatus;
    String category;
    String image;
    int rating;
}
