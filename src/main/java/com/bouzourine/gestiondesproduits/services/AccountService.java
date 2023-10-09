package com.bouzourine.gestiondesproduits.services;



import com.bouzourine.gestiondesproduits.entities.AppRole;
import com.bouzourine.gestiondesproduits.entities.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
}
