package com.bouzourine.gestiondesproduits.services;


import com.bouzourine.gestiondesproduits.entities.AppRole;
import com.bouzourine.gestiondesproduits.entities.AppUser;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.repositories.AppRoleRepository;
import com.bouzourine.gestiondesproduits.repositories.AppUserRepository;
import com.bouzourine.gestiondesproduits.services.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String passwod = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(passwod));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException("User"));
        AppRole appRole = appRoleRepository.findByRoleName(roleName)
                .orElseThrow(()-> new EntityNotFoundException("Role"));
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("User"));
    }

}
