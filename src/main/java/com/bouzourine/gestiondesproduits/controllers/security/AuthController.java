package com.bouzourine.gestiondesproduits.controllers.security;

import com.bouzourine.gestiondesproduits.dtos.jwttoken.JwtTokenRequestDto;
import com.bouzourine.gestiondesproduits.dtos.jwttoken.JwtTokenResponseDto;
import com.bouzourine.gestiondesproduits.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            description = "Authenticate and Obtain JWT Token",
            summary = "This operation authenticates a user and returns a JWT token for access.",
            responses = {
                    @ApiResponse(
                            description = "Success - Authentication successful, JWT token is provided.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokenResponseDto.class, description = "JWT Token Response")
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized - Authentication failed, invalid credentials.",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/token")
    public ResponseEntity<JwtTokenResponseDto> jwtToken(@RequestBody @Valid JwtTokenRequestDto jwtTokenDto) {
        return new ResponseEntity<>(authService.jwtToken(jwtTokenDto), HttpStatus.OK);
    }

}
