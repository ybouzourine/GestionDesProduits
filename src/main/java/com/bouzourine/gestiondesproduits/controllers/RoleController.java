package com.bouzourine.gestiondesproduits.controllers;

import com.bouzourine.gestiondesproduits.controllers.api.RoleControllerInterface;
import com.bouzourine.gestiondesproduits.dtos.RoleCreationDto;
import com.bouzourine.gestiondesproduits.dtos.RoleResponseDto;
import com.bouzourine.gestiondesproduits.dtos.RoleUpdateDto;
import com.bouzourine.gestiondesproduits.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController implements RoleControllerInterface {

    private RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    @PostMapping
    public ResponseEntity<RoleResponseDto> create(@RequestBody @Valid RoleCreationDto roleCreationDto) {
        return ResponseEntity.ok(roleService.create(roleCreationDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<RoleResponseDto> update(@PathVariable Long id,@RequestBody @Valid RoleUpdateDto roleUpdateDto) {
        return ResponseEntity.ok(roleService.update(id, roleUpdateDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
