package com.bouzourine.gestiondesproduits.controllers.api;

import com.bouzourine.gestiondesproduits.dtos.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.ProductUpdateDto;
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

@Tag(name = "Product")
public interface ProductControllerInterface {
    @Operation(
            description = "Create a New Product",
            summary = "This operation allows you to create a new product.",
            responses = {
                    @ApiResponse(
                            description = "Success - Product creation was successful. The product has been created and successfully recorded in the database.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict - This response indicates a conflict when trying to create a product with a name or code that already exists in the database.",
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
    ResponseEntity<ProductResponseDto> create(@RequestBody(description = "product to be created.") ProductCreationDto productCreationDto);

    @Operation(
            description = "Retrieve a List of All Products",
            summary = "This operation retrieves a list of all products from the database or an empty list if no products exist.",
            responses = {
                    @ApiResponse(
                            description = "Success - The list of products has been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ProductResponseDto.class)
                                    )
                            )
                    )
            }

    )
    ResponseEntity<List<ProductResponseDto>> getAll();

    @Operation(
            description = "Retrieve Product Details by ID",
            summary = "This operation allows you to retrieve details for a product by its unique ID.",
            responses = {
                    @ApiResponse(
                            description = "Success - Product details have been successfully retrieved.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found - This response indicates that the requested product does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<ProductResponseDto> getById(@Parameter(description = "id of product to be searched") Long id);

    @Operation(
            description = "Update a Product",
            summary = "This operation allows you to update a product if it exists.",
            responses = {
                    @ApiResponse(
                            description = "Success - Product update was successful. The product has been updated in the database.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Conflict - This response indicates a conflict when trying to update a product with a name or code that already exists in the database.",
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
                            description = "Not Found - This response indicates that the requested product does not exist.",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity<ProductResponseDto> update(@Parameter(description = "id of product to be updated.") Long id,@RequestBody(description = "product to be updated.") ProductUpdateDto productUpdateDto);

    @Operation(
            description = "Delete a Product",
            summary = "This operation deletes a product with the specified ID.",
            responses = {
                    @ApiResponse(
                            description = "No Content - The product has been successfully deleted.",
                            responseCode = "204"
                    )
            }

    )
    ResponseEntity<Void> delete(@Parameter(description = "id of product to be deleted.") Long id);
}