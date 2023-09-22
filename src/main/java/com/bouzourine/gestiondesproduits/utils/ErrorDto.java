package com.bouzourine.gestiondesproduits.utils;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {

    Integer httpCode;
    String message;
    List<String> errors;
}
