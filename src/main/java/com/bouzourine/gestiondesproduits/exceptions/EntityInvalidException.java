package com.bouzourine.gestiondesproduits.exceptions;

import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;

import java.util.List;

public class EntityInvalidException extends EntityException{

    public static final String MESSAGE = "ETITY_%s_INVALID";

    public EntityInvalidException(String entityType) {
        super(String.format(MESSAGE, entityType));
    }

    public EntityInvalidException(String entityType, List<String> errors){
        super(String.format(MESSAGE, entityType), errors);
    }
}
