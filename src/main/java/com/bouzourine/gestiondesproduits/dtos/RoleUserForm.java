package com.bouzourine.gestiondesproduits.dtos;

import com.bouzourine.gestiondesproduits.utils.RoleErrorCodes;
import com.bouzourine.gestiondesproduits.utils.UserErrorCodes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoleUserForm {

    @NotBlank(message = UserErrorCodes.USERNAME_FILED_CANNOT_BE_NULL_OR_BLANK)
    String username;
    @NotBlank(message = RoleErrorCodes.ROLENAME_FILED_CANNOT_BE_NULL_OR_BLANK)
    String roleName;
}
