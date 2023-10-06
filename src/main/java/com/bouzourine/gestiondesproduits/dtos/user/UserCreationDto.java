package com.bouzourine.gestiondesproduits.dtos.user;

import com.bouzourine.gestiondesproduits.utils.UserErrorCodes;
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
public class UserCreationDto {

    @Size(max = 30, message = UserErrorCodes.USERNAME_INVALID_SIZE)
    @NotBlank(message = UserErrorCodes.USERNAME_FILED_CANNOT_BE_NULL_OR_BLANK)
    private String username;
    @Size(max = 30, message = UserErrorCodes.PASSWORD_INVALID_SIZE)
    @NotBlank(message = UserErrorCodes.PASSWORD_FILED_CANNOT_BE_NULL_OR_BLANK)
    private String password;

}
