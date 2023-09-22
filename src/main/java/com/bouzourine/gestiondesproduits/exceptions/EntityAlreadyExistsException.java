package com.bouzourine.gestiondesproduits.exceptions;

import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;
import lombok.Getter;

import java.util.List;

@Getter
public class EntityAlreadyExistsException extends EntityException{

    public static final String MESSAGE = "ETITY_%s_ALREADY_EXIST";

    public EntityAlreadyExistsException(){
        super(String.format(MESSAGE, ProductErrorCodes.ENTITY_TYPE));
    }

    public EntityAlreadyExistsException(List<String> errors){
        super(String.format(MESSAGE, ProductErrorCodes.ENTITY_TYPE), errors);
    }


}
