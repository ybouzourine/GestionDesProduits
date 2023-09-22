package com.bouzourine.gestiondesproduits.exceptions;

import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;

import java.util.List;

public class EntityInvalidException extends EntityException{

    public static final String MESSAGE = "ETITY_%s_INVALID";

    public EntityInvalidException() {
        super(String.format(MESSAGE, ProductErrorCodes.ENTITY_TYPE));
    }

    public EntityInvalidException(List<String> errors){
        super(String.format(MESSAGE, ProductErrorCodes.ENTITY_TYPE), errors);
    }
}
