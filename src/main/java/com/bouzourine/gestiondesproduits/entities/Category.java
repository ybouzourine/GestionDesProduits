package com.bouzourine.gestiondesproduits.entities;

public enum Category {

    Accessories("Accessories"),
    Clothing("Clothing"),
    Electronics("Electronics"),
    Fitness("Fitness");

    private final String categoryName;

    Category(String categoryName){
        this.categoryName = categoryName;
    }

}
