package com.bouzourine.gestiondesproduits.dtos.jwttoken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class JwtTokenResponseDto {
    String accessToken;
    String refreshToken;
}
