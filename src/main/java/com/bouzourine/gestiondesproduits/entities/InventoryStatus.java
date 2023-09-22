package com.bouzourine.gestiondesproduits.entities;


public enum InventoryStatus {

    INSTOCK("INSTOCK"),
    LOWSTOCK("LOWSTOCK"),
    OUTOFSTOCK("OUTOFSTOCK");

    private final String status;

    InventoryStatus(String satuts){
        this.status = satuts;
    }

}
