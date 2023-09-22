package com.bouzourine.gestiondesproduits.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EntityException extends RuntimeException{
    private List<String> errors;
    public EntityException(String message){
        super(message);
    }
    public EntityException(String message, List<String> errors){
        super(message);
        this.errors = errors;
    }
}
