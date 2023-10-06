package com.bouzourine.gestiondesproduits.dtos;

import com.bouzourine.gestiondesproduits.utils.RoleErrorCodes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoleCreationDto {

    @Size(max = 30, message = RoleErrorCodes.ROLENAME_INVALID_SIZE)
    @NotBlank(message = RoleErrorCodes.ROLENAME_FILED_CANNOT_BE_NULL_OR_BLANK)
    private String roleName;
}
