package com.bouzourine.gestiondesproduits.exceptions;

import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;

import java.util.List;

public class EntityNotFoundException extends EntityException{
    public static final String MESSAGE = "ETITY_%s_NOT_FOUND";

    public EntityNotFoundException() {
        super(String.format(MESSAGE, ProductErrorCodes.ENTITY_TYPE));
    }

    public EntityNotFoundException(List<String> errors) {
        super(String.format(MESSAGE, ProductErrorCodes.ENTITY_TYPE), errors);
    }
}
