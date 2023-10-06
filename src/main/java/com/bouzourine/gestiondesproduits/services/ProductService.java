package com.bouzourine.gestiondesproduits.services;

import com.bouzourine.gestiondesproduits.dtos.product.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.product.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.product.ProductUpdateDto;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;

import java.util.List;

public interface ProductService {

    /**
     * Create a new product based on the provided information.
     *
     * @param productCreationDto The data for creating the new product.
     * @return A {@link ProductResponseDto} representing the newly created product.
     * @throws EntityAlreadyExistsException If a product with the same code or name already exists.
     */
    ProductResponseDto create(ProductCreationDto productCreationDto);

    /**
     * Retrieve a list of all products from the database.
     *
     * @return A list of {@link ProductResponseDto} representing all the products.
     */
    List<ProductResponseDto> getAll();

    /**
     * Retrieve product details for the specified ID.
     *
     * @param id The ID of the product to retrieve.
     * @return A {@link ProductResponseDto} representing the product with the specified ID.
     * @throws EntityNotFoundException If the product with the given ID is not found.
     */
    ProductResponseDto getById(Long id);

    /**
     * Update a product with the specified ID using the provided information.
     *
     * @param id               The ID of the product to be updated.
     * @param productUpdateDto The updated product information.
     * @return A {@link ProductResponseDto} representing the updated product.
     * @throws EntityNotFoundException   If the product with the given ID is not found.
     * @throws EntityInvalidException    If the updated data is invalid.
     */
    ProductResponseDto update(Long id, ProductUpdateDto productUpdateDto);

    /**
     * Delete a product from the database based on its ID.
     *
     * @param id The ID of the product to be deleted.
     */
    void delete(Long id);


}
