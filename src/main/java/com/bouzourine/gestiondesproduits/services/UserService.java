package com.bouzourine.gestiondesproduits.services;

import com.bouzourine.gestiondesproduits.dtos.*;
import com.bouzourine.gestiondesproduits.entities.User;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;

import java.util.List;

public interface UserService {

    /**
     * Create a new user based on the provided information.
     *
     * @param userCreationDto The data for creating the new user.
     * @return A {@link UserResponseDto} representing the newly created user.
     * @throws EntityAlreadyExistsException If a user with the same username already exists.
     */
    UserResponseDto create(UserCreationDto userCreationDto);

    /**
     * Retrieve a list of all users from the database.
     *
     * @return A list of {@link UserResponseDto} representing all the users.
     */
    List<UserResponseDto> getAll();

    /**
     * Retrieve user details for the specified ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A {@link UserResponseDto} representing the user with the specified ID.
     * @throws EntityNotFoundException If the user with the given ID is not found.
     */
    UserResponseDto getById(Long id);

    /**
     * Update a user with the specified ID using the provided information.
     *
     * @param id               The ID of the user to be updated.
     * @param userUpdateDto The updated user information.
     * @return A {@link UserResponseDto} representing the updated user.
     * @throws EntityNotFoundException   If the user with the given ID is not found.
     * @throws EntityInvalidException    If the updated data is invalid.
     */
    UserResponseDto update(Long id, UserUpdateDto userUpdateDto);

    /**
     * Delete a user from the database based on its ID.
     *
     * @param id The ID of the user to be deleted.
     */
    void delete(Long id);

    /**
     * Adds a role to an existing user.
     *
     * This method searches for a user by their username and a role by its role name
     * in the corresponding repositories. If the user and role exist, the role is added
     * to the user's list of roles, provided it is not already present.
     *
     * @param roleUserForm The form information containing the username and role name.
     * @throws EntityNotFoundException If the user or role is not found in the repositories.
     */
    void addRoleToUser(RoleUserForm roleUserForm);

    /**
     * Removes a role from an existing user.
     *
     * This method searches for a user by their username and a role by its role name
     * in the corresponding repositories. If the user and role exist, the role is removed
     * from the user's list of roles, provided it is currently present.
     *
     * @param roleUserForm The form information containing the username and role name.
     * @throws EntityNotFoundException If the user or role is not found in the repositories.
     */
    void removeRoleToUser(RoleUserForm roleUserForm);

    /**
     * Retrieve user details for the specified USERNAME.
     *
     * @param username The USERNAME of the user to retrieve.
     * @return A {@link UserResponseDto} representing the user with the specified Username.
     * @throws EntityNotFoundException If the user with the given username is not found.
     */
    User loadUserByUsername(String username);


}
