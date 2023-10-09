package com.bouzourine.gestiondesproduits.dtos.jwttoken;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JwtTokenRequestDto {

    @Schema(description = "The type of grant. Must be 'password' or 'refreshToken'.")
    @Pattern(regexp = "^(password|refreshToken)$", message = "grantType must be 'password' or 'refreshToken'.")
    String grantType;
    String username;
    String password;
    @NonNull
    boolean withRefreshToken;
    String refreshToken;

}
