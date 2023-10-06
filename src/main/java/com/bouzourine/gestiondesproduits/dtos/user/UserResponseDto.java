package com.bouzourine.gestiondesproduits.dtos.user;

import com.bouzourine.gestiondesproduits.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private Collection<Role> roles = new ArrayList<>();
}