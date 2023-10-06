package com.bouzourine.gestiondesproduits.controllers.api;

import com.bouzourine.gestiondesproduits.dtos.*;
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

@Tag(name = "Role")
public interface RoleControllerInterface {

    @Operation(
            description = "Create a New Role",
            summary = "This operation allows you to create a new role.",
            responses = {
                    @ApiResponse(
                            description = "Success - Role creation was successful. The role has been created and successfully recorded in the database.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict - This response indicates a conflict when trying to create a role with a roleName that already exists in the database.",
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
    ResponseEntity<RoleResponseDto> create(@RequestBody(description = "role to be created.") RoleCreationDto roleCreationDto);

    @Operation(
            description = "Retrieve a List of All Roles",
            summary = "This operation retrieves a list of all roles from the database or an empty list if no roles exist.",
            responses = {
                    @ApiResponse(
                            description = "Success - The list of roles has been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = RoleResponseDto.class)
                                    )
                            )
                    )
            }

    )
    ResponseEntity<List<RoleResponseDto>> getAll();

    @Operation(
            description = "Retrieve Role Details by ID",
            summary = "This operation allows you to retrieve details for a role by its unique ID.",
            responses = {
                    @ApiResponse(
                            description = "Success - Role details have been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - This response indicates that the requested role does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<RoleResponseDto> getById(@Parameter(description = "id of role to be searched") Long id);

    @Operation(
            description = "Update a Role",
            summary = "This operation allows you to update a role if it exists.",
            responses = {
                    @ApiResponse(
                            description = "Success - Role update was successful. The role has been updated in the database.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict - This response indicates a conflict when trying to update a role with a roleName that already exists in the database.",
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
                    ),
                    @ApiResponse(
                            description = "Not Found - This response indicates that the requested role does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<RoleResponseDto> update(@Parameter(description = "id of role to be updated.") Long id,@RequestBody(description = "role to be updated.") RoleUpdateDto roleUpdateDto);

    @Operation(
            description = "Delete a Role",
            summary = "This operation deletes a role with the specified ID.",
            responses = {
                    @ApiResponse(
                            description = "No Content - The role has been successfully deleted.",
                            responseCode = "204"
                    )
            }

    )
    ResponseEntity<Void> delete(@Parameter(description = "id of role to be deleted.") Long id);
}
