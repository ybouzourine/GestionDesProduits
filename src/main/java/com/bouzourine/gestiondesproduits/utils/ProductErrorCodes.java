package com.bouzourine.gestiondesproduits.utils;

public class ProductErrorCodes {

    public static final String ENTITY_TYPE = "product";
    public static final String CODE_FILED_INVALID_SIZE = "code field must contain exactly 9 characters.";
    public static final String CODE_FILED_CANNOT_BE_NULL_OR_BLANK = "code field cannot be null or blank.";
    public static final String NAME_FIELD_INVALID_SIZE = "name field cannot exceed 30 characters.";
    public static final String NAME_FIELD_CANNOT_BE_NULL_OR_BLANK = "name field cannot be null or blank.";
    public static final String DESCRIPTION_FIELD_INVALID_SIZE = "description field cannot exceed 225 characters.";
    public static final String DESCRIPTION_FIELD_CANNOT_BE_NULL_OR_BLANK = "description field cannot be null or blank.";
    public static final String PRICE_FIELD_CANNOT_BE_NEGATIVE = "price field cannot be negative.";
    public static final String QUANTITY_FIELD_CANNOT_BE_NEGATIVE = "quantity field cannot be negative.";
    public static final String RATING_FIELD_CANNOT_BE_NEGATIVE = "rating field cannot be negative.";
    public static final String NAME_PRODUCT_ALREADY_EXISTS = "name already exists.";
    public static final String CODE_PRODUCT_ALREADY_EXISTS = "code already exists.";

    private ProductErrorCodes(){

    }
}
