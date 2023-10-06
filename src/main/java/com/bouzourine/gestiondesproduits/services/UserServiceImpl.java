package com.bouzourine.gestiondesproduits.services;

import com.bouzourine.gestiondesproduits.dtos.RoleUserForm;
import com.bouzourine.gestiondesproduits.dtos.UserCreationDto;
import com.bouzourine.gestiondesproduits.dtos.UserResponseDto;
import com.bouzourine.gestiondesproduits.dtos.UserUpdateDto;
import com.bouzourine.gestiondesproduits.entities.Role;
import com.bouzourine.gestiondesproduits.entities.User;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.repositories.RoleRepository;
import com.bouzourine.gestiondesproduits.repositories.UserRepository;
import com.bouzourine.gestiondesproduits.utils.RoleErrorCodes;
import com.bouzourine.gestiondesproduits.utils.UserErrorCodes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserResponseDto create(UserCreationDto userCreationDto) {
        User user = modelMapper.map(userCreationDto , User.class);
        List<String> errors = new ArrayList<>();
        if (userRepository.isUsernameExistsInUsers(user.getUsername())) {
            errors.add(UserErrorCodes.USERNAME_ALREADY_EXISTS);
        }
        if (!errors.isEmpty()) {
            throw new EntityAlreadyExistsException(UserErrorCodes.ENTITY_TYPE, errors);
        }
        User userSaved = userRepository.saveAndFlush(user);
        return modelMapper.map(userSaved, UserResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user,UserResponseDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public UserResponseDto getById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .orElseThrow(()->new EntityNotFoundException(UserErrorCodes.ENTITY_TYPE));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserResponseDto update(Long id, UserUpdateDto userUpdateDto) {
        User userToUpdated = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(UserErrorCodes.ENTITY_TYPE));
        List<String> errors = new ArrayList<>();
        if (!userToUpdated.getUsername().equals(userUpdateDto.getUsername()) && userRepository.isUsernameExistsInUsers(userUpdateDto.getUsername())) {
            errors.add(UserErrorCodes.USERNAME_ALREADY_EXISTS);
        }
        if (!errors.isEmpty()) {
           throw new EntityInvalidException(UserErrorCodes.ENTITY_TYPE, errors);
        }
        userToUpdated.setUsername(userUpdateDto.getUsername());
        return modelMapper.map(userToUpdated, UserResponseDto.class);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void addRoleToUser(RoleUserForm roleUserForm) {
        User user = userRepository.findByUsername(roleUserForm.getUsername())
                .orElseThrow(()->new EntityNotFoundException(UserErrorCodes.ENTITY_TYPE));
        Role role = roleRepository.findByRoleName(roleUserForm.getRoleName())
                .orElseThrow(()->new EntityNotFoundException(RoleErrorCodes.ENTITY_TYPE));
        if (!user.getRoles().contains(role)) {
          user.getRoles().add(role);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeRoleToUser(RoleUserForm roleUserForm) {
        User user = userRepository.findByUsername(roleUserForm.getUsername())
                .orElseThrow(()->new EntityNotFoundException(UserErrorCodes.ENTITY_TYPE));
        Role role = roleRepository.findByRoleName(roleUserForm.getRoleName())
                .orElseThrow(()->new EntityNotFoundException(RoleErrorCodes.ENTITY_TYPE));
        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException(UserErrorCodes.ENTITY_TYPE));
    }
}
