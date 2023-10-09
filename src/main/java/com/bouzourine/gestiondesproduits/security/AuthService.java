package com.bouzourine.gestiondesproduits.security;

import com.bouzourine.gestiondesproduits.dtos.jwttoken.JwtTokenRequestDto;
import com.bouzourine.gestiondesproduits.dtos.jwttoken.JwtTokenResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public AuthService(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager, JwtDecoder jwtDecoder, UserDetailsService userDetailsService) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
    }



    public JwtTokenResponseDto jwtToken(JwtTokenRequestDto jwtTokenDto){
        String subject=null;
        String scope=null;
        if (jwtTokenDto.getGrantType().equals("password")) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtTokenDto.getUsername(), jwtTokenDto.getPassword())
            );
            subject=authentication.getName();
            scope=authentication.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(" "));
        } else if (jwtTokenDto.getGrantType().equals("refreshToken")) {
            if (jwtTokenDto.getRefreshToken() == null) {
                throw new RuntimeException("Refreshe token is required");
            }
            Jwt decodeJWT = null;
            try {
                decodeJWT = jwtDecoder.decode(jwtTokenDto.getRefreshToken());
            }   catch (JwtException jwtException) {
                throw new RuntimeException(jwtException.getMessage());
            }
            subject = decodeJWT.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            scope = authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        }

        JwtTokenResponseDto jwtTokenResponseDto = new JwtTokenResponseDto();
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(jwtTokenDto.isWithRefreshToken()?5:30, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope", scope)
                .build();
        String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        jwtTokenResponseDto.setAccessToken(jwtAccessToken);
        if(jwtTokenDto.isWithRefreshToken()){
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(30, ChronoUnit.MINUTES))
                    .issuer("security-service")
                    .build();
            String jwtRefreshToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
            jwtTokenResponseDto.setRefreshToken(jwtRefreshToken);
        }
        return jwtTokenResponseDto;
    }
}
