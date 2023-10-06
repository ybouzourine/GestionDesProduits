package com.bouzourine.gestiondesproduits.dtos.product;

import com.bouzourine.gestiondesproduits.entities.Category;
import com.bouzourine.gestiondesproduits.entities.InventoryStatus;
import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductUpdateDto {

    @Pattern(regexp = "^.{9}$", message = ProductErrorCodes.CODE_FILED_INVALID_SIZE)
    @NotBlank(message = ProductErrorCodes.CODE_FILED_CANNOT_BE_NULL_OR_BLANK)
    String code;
    @Size(max = 30, message = ProductErrorCodes.NAME_FIELD_INVALID_SIZE)
    @NotBlank(message = ProductErrorCodes.NAME_FIELD_CANNOT_BE_NULL_OR_BLANK)
    String name;
    @Size(max = 225, message = ProductErrorCodes.DESCRIPTION_FIELD_INVALID_SIZE)
    @NotBlank(message = ProductErrorCodes.DESCRIPTION_FIELD_CANNOT_BE_NULL_OR_BLANK)
    String description;
    @PositiveOrZero(message = ProductErrorCodes.PRICE_FIELD_CANNOT_BE_NEGATIVE)
    int price;
    @PositiveOrZero(message = ProductErrorCodes.QUANTITY_FIELD_CANNOT_BE_NEGATIVE)
    int quantity;
    InventoryStatus inventoryStatus;
    Category category;
    String image;
    @PositiveOrZero(message = ProductErrorCodes.RATING_FIELD_CANNOT_BE_NEGATIVE)
    int rating;

}
