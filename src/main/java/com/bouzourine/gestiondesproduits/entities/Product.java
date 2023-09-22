package com.bouzourine.gestiondesproduits.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 30)
    private String code;
    @Column(name = "name", length = 30)
    private String name;
    @Column(name = "description", length = 225)
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "inventoryStatus", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    @Column(name = "category", length = 30)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name = "image")
    private String image;
    @Column(name = "rating")
    private int rating;

}
