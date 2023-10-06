package com.bouzourine.gestiondesproduits.services;

import com.bouzourine.gestiondesproduits.dtos.RoleCreationDto;
import com.bouzourine.gestiondesproduits.dtos.RoleResponseDto;
import com.bouzourine.gestiondesproduits.dtos.RoleUpdateDto;
import com.bouzourine.gestiondesproduits.entities.Role;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.repositories.RoleRepository;
import com.bouzourine.gestiondesproduits.utils.RoleErrorCodes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RoleResponseDto create(RoleCreationDto roleCreationDto) {
        Role role = modelMapper.map(roleCreationDto, Role.class);
        List<String> errors = new ArrayList<>();
        if (roleRepository.isRoleNameExistsInRoles(role.getRoleName())) {
            errors.add(RoleErrorCodes.ROLENAME_ALREADY_EXISTS);
        }
        if (!errors.isEmpty()) {
            throw new EntityAlreadyExistsException(RoleErrorCodes.ENTITY_TYPE, errors);
        }
        Role roleSaved = roleRepository.saveAndFlush(role);
        return modelMapper.map(roleSaved, RoleResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<RoleResponseDto> getAll() {
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleResponseDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public RoleResponseDto getById(Long id) {
        return roleRepository.findById(id)
                .map(role -> modelMapper.map(role, RoleResponseDto.class))
                .orElseThrow(()-> new EntityNotFoundException(RoleErrorCodes.ENTITY_TYPE));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public RoleResponseDto update(Long id, RoleUpdateDto roleUpdateDto) {
        Role roleToUpdated = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(RoleErrorCodes.ENTITY_TYPE));
        List<String> errors = new ArrayList<>();
        if (!roleToUpdated.getRoleName().equals(roleUpdateDto.getRoleName()) && roleRepository.isRoleNameExistsInRoles(roleUpdateDto.getRoleName())) {
            errors.add(RoleErrorCodes.ROLENAME_ALREADY_EXISTS);
        }
        if (!errors.isEmpty()) {
            throw new EntityInvalidException(RoleErrorCodes.ENTITY_TYPE, errors);
        }
        roleToUpdated.setRoleName(roleUpdateDto.getRoleName());
        return modelMapper.map(roleToUpdated, RoleResponseDto.class);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
