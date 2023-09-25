package com.bouzourine.gestiondesproduits.controllers;

import com.bouzourine.gestiondesproduits.controllers.api.ProductControllerInterface;
import com.bouzourine.gestiondesproduits.dtos.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.ProductUpdateDto;
import com.bouzourine.gestiondesproduits.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Ajouter l'autorisation : Role -> CLIENT
@RestController
@RequestMapping("/products")
public class ProductController implements ProductControllerInterface {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //Ajouter l'autorisation : seuls les administrateurs peuvent créer un nouveau produit.
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductCreationDto productCreationDto){
        return ResponseEntity.ok(productService.create(productCreationDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    //Ajouter l'autorisation : seuls les administrateurs peuvent effectuer la mise à jour d'un produit.
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody @Valid ProductUpdateDto productUpdateDto){
        return ResponseEntity.ok(productService.update(id, productUpdateDto));
    }

    //Ajouter l'autorisation : seul l'administrateur peut supprimer un produit.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
