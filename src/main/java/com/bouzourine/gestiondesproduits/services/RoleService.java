package com.bouzourine.gestiondesproduits.services;

import com.bouzourine.gestiondesproduits.dtos.role.RoleCreationDto;
import com.bouzourine.gestiondesproduits.dtos.role.RoleResponseDto;
import com.bouzourine.gestiondesproduits.dtos.role.RoleUpdateDto;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;

import java.util.List;

public interface RoleService {

    /**
     * Create a new role based on the provided information.
     *
     * @param roleCreationDto The data for creating the new role.
     * @return A {@link RoleResponseDto} representing the newly created role.
     * @throws EntityAlreadyExistsException If a role with the same roleName already exists.
     */
    RoleResponseDto create(RoleCreationDto roleCreationDto);

    /**
     * Retrieve a list of all roles from the database.
     *
     * @return A list of {@link RoleResponseDto} representing all the roles.
     */
    List<RoleResponseDto> getAll();

    /**
     * Retrieve role details for the specified ID.
     *
     * @param id The ID of the role to retrieve.
     * @return A {@link RoleResponseDto} representing the role with the specified ID.
     * @throws EntityNotFoundException If the role with the given ID is not found.
     */
    RoleResponseDto getById(Long id);

    /**
     * Update a role with the specified ID using the provided information.
     *
     * @param id               The ID of the role to be updated.
     * @param roleUpdateDto The updated role information.
     * @return A {@link RoleResponseDto} representing the updated role.
     * @throws EntityNotFoundException   If the role with the given ID is not found.
     * @throws EntityInvalidException    If the updated data is invalid.
     */
    RoleResponseDto update(Long id, RoleUpdateDto roleUpdateDto);

    /**
     * Delete a role from the database based on its ID.
     *
     * @param id The ID of the role to be deleted.
     */
    void delete(Long id);
}
