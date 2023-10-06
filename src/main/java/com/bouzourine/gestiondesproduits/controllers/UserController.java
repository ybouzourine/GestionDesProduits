package com.bouzourine.gestiondesproduits.controllers;

import com.bouzourine.gestiondesproduits.controllers.api.UserControllerInterface;
import com.bouzourine.gestiondesproduits.dtos.RoleUserForm;
import com.bouzourine.gestiondesproduits.dtos.UserCreationDto;
import com.bouzourine.gestiondesproduits.dtos.UserResponseDto;
import com.bouzourine.gestiondesproduits.dtos.UserUpdateDto;
import com.bouzourine.gestiondesproduits.entities.User;
import com.bouzourine.gestiondesproduits.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerInterface {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Override
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreationDto userCreationDto){
        return ResponseEntity.ok(userService.create(userCreationDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,@RequestBody @Valid UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.update(id, userUpdateDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/addRoleToUser")
    public ResponseEntity<Void> addRoleToUser(@RequestBody @Valid RoleUserForm roleUserForm) {
        userService.addRoleToUser(roleUserForm);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/removeRoleToUser")
    public ResponseEntity<Void> removeRoleToUser(@RequestBody @Valid RoleUserForm roleUserForm) {
        userService.removeRoleToUser(roleUserForm);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.loadUserByUsername(username));
    }
}
