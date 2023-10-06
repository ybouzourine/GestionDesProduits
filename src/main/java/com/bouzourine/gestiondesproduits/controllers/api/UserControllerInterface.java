package com.bouzourine.gestiondesproduits.controllers.api;

import com.bouzourine.gestiondesproduits.dtos.role.RoleUserForm;
import com.bouzourine.gestiondesproduits.dtos.user.UserCreationDto;
import com.bouzourine.gestiondesproduits.dtos.user.UserResponseDto;
import com.bouzourine.gestiondesproduits.dtos.user.UserUpdateDto;
import com.bouzourine.gestiondesproduits.entities.User;
import com.bouzourine.gestiondesproduits.utils.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User")
public interface UserControllerInterface {

    @Operation(
            description = "Create a New User",
            summary = "This operation allows you to create a new user.",
            responses = {
                    @ApiResponse(
                            description = "Success - user creation was successful. The user has been created and successfully recorded in the database.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict - This response indicates a conflict when trying to create an user with an username that already exists in the database.",
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request - This response is returned when the provided data is invalid or when deserialization fails.",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<UserResponseDto> create(@RequestBody(description = "user to be created.") UserCreationDto userCreationDto);

    @Operation(
            description = "Retrieve a List of All Users",
            summary = "This operation retrieves a list of all users from the database or an empty list if no user exist.",
            responses = {
                    @ApiResponse(
                            description = "Success - The list of users has been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserResponseDto.class)
                                    )
                            )
                    )
            }

    )
    ResponseEntity<List<UserResponseDto>> getAll();

    @Operation(
            description = "Retrieve User Details by ID",
            summary = "This operation allows you to retrieve details for a user by its unique ID.",
            responses = {
                    @ApiResponse(
                            description = "Success - User details have been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - This response indicates that the requested user does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<UserResponseDto> getById(@Parameter(description = "id of user to be searched") Long id);

    @Operation(
            description = "Update a user",
            summary = "This operation allows you to update an user if it exists.",
            responses = {
                    @ApiResponse(
                            description = "Success - User update was successful. The user has been updated in the database.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict - This response indicates a conflict when trying to update an user with an username that already exists in the database.",
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request - This response is returned when the provided data is invalid.",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - This response indicates that the requested user does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<UserResponseDto> update(@Parameter(description = "id of user to be updated.") Long id,@RequestBody(description = "user to be updated.") UserUpdateDto userUpdateDto);

    @Operation(
            description = "Delete a User",
            summary = "This operation deletes an user with the specified ID.",
            responses = {
                    @ApiResponse(
                            description = "No Content - The user has been successfully deleted.",
                            responseCode = "204"
                    )
            }

    )
    ResponseEntity<Void> delete(@Parameter(description = "id of user to be deleted.") Long id);


    @Operation(
            description = "Adds a role to an existing user.",
            summary = "This operation allows you to associate an existing role with an existing user.",
            responses = {
                    @ApiResponse(
                            description = "No Content - Role added successfully to the user.",
                            responseCode = "204",
                            content = @Content(
                                    schema = @Schema()
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - Indicates that either the user or the role does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> addRoleToUser(@RequestBody(description = "Form information containing the username and role name.") RoleUserForm roleUserForm);

    @Operation(
            description = "Removes a role from an existing user.",
            summary = "This operation allows you to disassociate an existing role from an existing user.",
            responses = {
                    @ApiResponse(
                            description = "No Content - Role removed successfully from the user.",
                            responseCode = "204",
                            content = @Content(
                                    schema = @Schema()
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - Indicates that either the user or the role does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> removeRoleToUser(@RequestBody(description = "Form information containing the username and role name.") RoleUserForm roleUserForm);

    @Operation(
            description = "Retrieve User Details by Username",
            summary = "This operation allows you to retrieve details for a user by its unique Username.",
            responses = {
                    @ApiResponse(
                            description = "Success - User details have been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - This response indicates that the requested user does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<User> getUserByUsername(@Parameter(description = "username of user to be searched") String username);
}
