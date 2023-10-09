package com.bouzourine.gestiondesproduits.services;

import com.bouzourine.gestiondesproduits.dtos.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.ProductUpdateDto;
import com.bouzourine.gestiondesproduits.entities.Category;
import com.bouzourine.gestiondesproduits.entities.InventoryStatus;
import com.bouzourine.gestiondesproduits.entities.Product;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.repositories.ProductRepository;
import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ProductResponseDto create(ProductCreationDto productCreationDto) {
        Product product = modelMapper.map(productCreationDto, Product.class);
        List<String> errors = new ArrayList<>();
        if (productRepository.isCodeExistsInProducts(product.getCode()) ) {
            errors.add(ProductErrorCodes.CODE_PRODUCT_ALREADY_EXISTS);
        }
        if (productRepository.isNameExistsInProducts(product.getName())) {
            errors.add(ProductErrorCodes.NAME_PRODUCT_ALREADY_EXISTS);
        }
        if (!errors.isEmpty()) {
            throw new EntityAlreadyExistsException(ProductErrorCodes.ENTITY_TYPE, errors);
        }
        Product productSaved = productRepository.saveAndFlush(product);
        return modelMapper.map(productSaved, ProductResponseDto.class);
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ProductResponseDto getById(Long id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .orElseThrow(() ->new  EntityNotFoundException(ProductErrorCodes.ENTITY_TYPE));
    }


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ProductResponseDto update(Long id, ProductUpdateDto productUpdateDto) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() ->new EntityNotFoundException(ProductErrorCodes.ENTITY_TYPE));
        List<String> errors = new ArrayList<>();
        if (!oldProduct.getCode().equals(productUpdateDto.getCode()) && productRepository.isCodeExistsInProducts(productUpdateDto.getCode())) {
            errors.add(ProductErrorCodes.CODE_PRODUCT_ALREADY_EXISTS);
        }
        if (!oldProduct.getName().equals(productUpdateDto.getName()) && productRepository.isNameExistsInProducts(productUpdateDto.getName())) {
            errors.add(ProductErrorCodes.NAME_PRODUCT_ALREADY_EXISTS);
        }
        if (!errors.isEmpty()) {
            throw new EntityInvalidException(ProductErrorCodes.ENTITY_TYPE, errors);
        }
        oldProduct.setCode(productUpdateDto.getCode());
        oldProduct.setName(productUpdateDto.getName());
        oldProduct.setDescription(productUpdateDto.getDescription());
        oldProduct.setPrice(productUpdateDto.getPrice());
        oldProduct.setQuantity(productUpdateDto.getQuantity());
        oldProduct.setInventoryStatus(InventoryStatus.valueOf(productUpdateDto.getInventoryStatus().toString()));
        oldProduct.setCategory(Category.valueOf(productUpdateDto.getCategory().toString()));
        oldProduct.setImage(productUpdateDto.getImage());
        oldProduct.setRating(productUpdateDto.getRating());
        return modelMapper.map(oldProduct, ProductResponseDto.class);
    }

    @Override
    public void delete(Long id) {
         productRepository.deleteById(id);
    }
}
