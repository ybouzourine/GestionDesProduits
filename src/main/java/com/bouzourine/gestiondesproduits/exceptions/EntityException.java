package com.bouzourine.gestiondesproduits.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EntityException extends RuntimeException{
    private List<String> errors;

    public EntityException(String entityType){
        super(entityType);
    }
    public EntityException(String entityType, List<String> errors){
        super(entityType);
        this.errors = errors;
    }
}
