package com.bouzourine.gestiondesproduits.controllers;

import com.bouzourine.gestiondesproduits.controllers.api.ProductControllerInterface;
import com.bouzourine.gestiondesproduits.dtos.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.ProductUpdateDto;
import com.bouzourine.gestiondesproduits.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController implements ProductControllerInterface {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductCreationDto productCreationDto){
        return ResponseEntity.ok(productService.create(productCreationDto));
    }
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_USER') || hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<ProductResponseDto>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') || hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @Override
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody @Valid ProductUpdateDto productUpdateDto){
        return ResponseEntity.ok(productService.update(id, productUpdateDto));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
