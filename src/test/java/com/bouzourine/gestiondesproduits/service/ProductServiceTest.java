package com.bouzourine.gestiondesproduits.service;

import com.bouzourine.gestiondesproduits.dtos.product.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.product.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.product.ProductUpdateDto;
import com.bouzourine.gestiondesproduits.entities.Category;
import com.bouzourine.gestiondesproduits.entities.InventoryStatus;
import com.bouzourine.gestiondesproduits.entities.Product;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.repositories.ProductRepository;
import com.bouzourine.gestiondesproduits.services.ProductService;
import com.bouzourine.gestiondesproduits.services.ProductServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @BeforeAll
    void setUp(){
        productService = new ProductServiceImpl(productRepositoryMock, modelMapperMock);
    }

    @AfterEach
    void restMock(){
        Mockito.reset(productRepositoryMock);
    }

    private final ProductCreationDto defaultProductCreationDto = ProductCreationDto.builder()
            .code("123456789")
            .name("nameTest")
            .description("description")
            .price(10)
            .quantity(100)
            .inventoryStatus(InventoryStatus.INSTOCK)
            .category(Category.Accessories)
            .image("image.png")
            .rating(10)
            .build();
    private final Product defaultProductEntity = Product.builder()
            .id(1L)
            .code("123456789")
            .name("nameTest")
            .description("description")
            .price(10)
            .quantity(100)
            .inventoryStatus(InventoryStatus.INSTOCK)
            .category(Category.Accessories)
            .image("image.png")
            .rating(10)
            .build();

    private final ProductUpdateDto defaultProductUpdateDto = ProductUpdateDto.builder()
            .code("123456789")
            .name("new name")
            .description("new description")
            .price(10)
            .quantity(100)
            .inventoryStatus(InventoryStatus.INSTOCK)
            .category(Category.Accessories)
            .image("new image.png")
            .rating(10)
            .build();

    private final ProductResponseDto defaultProductResponseDto = ProductResponseDto.builder()
            .id(1L)
            .code("123456789")
            .name("nameTest")
            .description("description")
            .price(10)
            .quantity(100)
            .inventoryStatus(InventoryStatus.INSTOCK.name())
            .category(Category.Accessories.name())
            .image("image.png")
            .rating(10)
            .build();

    private final ProductResponseDto defaultProductUpdateRespenseDto = ProductResponseDto.builder()
            .id(1L)
            .code("123456789")
            .name("new name")
            .description("new description")
            .price(10)
            .quantity(100)
            .inventoryStatus(InventoryStatus.INSTOCK.name())
            .category(Category.Accessories.name())
            .image("new image.png")
            .rating(10)
            .build();

    private final List<Product> defaultProductEntityList = Arrays.asList(
            Product.builder()
                    .id(1L)
                    .code("123456789")
                    .name("Product 1")
                    .description("Description 1")
                    .price(10)
                    .quantity(100)
                    .inventoryStatus(InventoryStatus.INSTOCK)
                    .category(Category.Accessories)
                    .image("image1.png")
                    .rating(4)
                    .build(),

            Product.builder()
                    .id(2L)
                    .code("987654321")
                    .name("Product 2")
                    .description("Description 2")
                    .price(20)
                    .quantity(50)
                    .inventoryStatus(InventoryStatus.INSTOCK)
                    .category(Category.Electronics)
                    .image("image2.png")
                    .rating(5)
                    .build(),

            Product.builder()
                    .id(3L)
                    .code("567890123")
                    .name("Product 3")
                    .description("Description 3")
                    .price(30)
                    .quantity(200)
                    .inventoryStatus(InventoryStatus.OUTOFSTOCK)
                    .category(Category.Clothing)
                    .image("image3.png")
                    .rating(4)
                    .build()
    );
    private final List<ProductResponseDto> defaultProductResponseDtoList = Arrays.asList(
            ProductResponseDto.builder()
                    .id(1L)
                    .code("123456789")
                    .name("Product 1")
                    .description("Description 1")
                    .price(10)
                    .quantity(100)
                    .inventoryStatus(InventoryStatus.INSTOCK.name())
                    .category(Category.Accessories.name())
                    .image("image1.png")
                    .rating(4)
                    .build(),

            ProductResponseDto.builder()
                    .id(2L)
                    .code("987654321")
                    .name("Product 2")
                    .description("Description 2")
                    .price(20)
                    .quantity(50)
                    .inventoryStatus(InventoryStatus.INSTOCK.name())
                    .category(Category.Electronics.name())
                    .image("image2.png")
                    .rating(5)
                    .build(),

            ProductResponseDto.builder()
                    .id(3L)
                    .code("567890123")
                    .name("Product 3")
                    .description("Description 3")
                    .price(30)
                    .quantity(200)
                    .inventoryStatus(InventoryStatus.OUTOFSTOCK.name())
                    .category(Category.Clothing.name())
                    .image("image3.png")
                    .rating(4)
                    .build()
    );

    @Test
    void givenValidProductCreationDto_whenCreatProduct_ThenReturnProductRespenseDto(){

        Mockito.when(modelMapperMock.map(defaultProductCreationDto, Product.class)).thenReturn(defaultProductEntity);
        Mockito.when(productRepositoryMock.isNameExistsInProducts(defaultProductEntity.getName())).thenReturn(false);
        Mockito.when(productRepositoryMock.isCodeExistsInProducts(defaultProductEntity.getCode())).thenReturn(false);
        Mockito.when(productRepositoryMock.saveAndFlush(defaultProductEntity)).thenReturn(defaultProductEntity);
        Mockito.when(modelMapperMock.map(defaultProductEntity, ProductResponseDto.class)).thenReturn(defaultProductResponseDto);

        ProductResponseDto expectedProductResponseDto = defaultProductResponseDto;
        ProductResponseDto actualProductResponseDto = productService.create(defaultProductCreationDto);

        Assertions.assertEquals(expectedProductResponseDto.getId(), actualProductResponseDto.getId());
        Assertions.assertEquals(expectedProductResponseDto.getCode(), actualProductResponseDto.getCode());
        Assertions.assertEquals(expectedProductResponseDto.getName(), actualProductResponseDto.getName());
        Assertions.assertEquals(expectedProductResponseDto.getDescription(), actualProductResponseDto.getDescription());
        Assertions.assertEquals(expectedProductResponseDto.getPrice(), actualProductResponseDto.getPrice());
        Assertions.assertEquals(expectedProductResponseDto.getQuantity(), actualProductResponseDto.getQuantity());
        Assertions.assertEquals(expectedProductResponseDto.getInventoryStatus(), actualProductResponseDto.getInventoryStatus());
        Assertions.assertEquals(expectedProductResponseDto.getCategory(), actualProductResponseDto.getCategory());
        Assertions.assertEquals(expectedProductResponseDto.getImage(), actualProductResponseDto.getImage());
        Assertions.assertEquals(expectedProductResponseDto.getRating(), actualProductResponseDto.getRating());
    }

    @Test
    void givenProductCreationDto_withCodeAlreadyExists_whenCreatProduct_ThenThrowEntityAlreadyExistsException(){

        Mockito.when(modelMapperMock.map(defaultProductCreationDto, Product.class)).thenReturn(defaultProductEntity);

        Mockito.when(productRepositoryMock.isNameExistsInProducts(defaultProductEntity.getName())).thenReturn(true);
        Mockito.when(productRepositoryMock.isCodeExistsInProducts(defaultProductEntity.getCode())).thenReturn(false);

        assertThrows(EntityAlreadyExistsException.class, () -> productService.create(defaultProductCreationDto));

    }
    @Test
    void givenProductCreationDto_withNameAlreadyExists_whenCreatProduct_ThenThrowEntityAlreadyExistsException(){

        Mockito.when(modelMapperMock.map(defaultProductCreationDto, Product.class)).thenReturn(defaultProductEntity);

        Mockito.when(productRepositoryMock.isNameExistsInProducts(defaultProductEntity.getName())).thenReturn(false);
        Mockito.when(productRepositoryMock.isCodeExistsInProducts(defaultProductEntity.getCode())).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> productService.create(defaultProductCreationDto));

    }

    @Test
    void getAll_ThenReturnListOfProductRespenseDto(){

        Mockito.when(productRepositoryMock.findAll()).thenReturn(defaultProductEntityList);

        Mockito.when(modelMapperMock.map(defaultProductEntityList.get(0), ProductResponseDto.class)).thenReturn(defaultProductResponseDtoList.get(0));
        Mockito.when(modelMapperMock.map(defaultProductEntityList.get(1), ProductResponseDto.class)).thenReturn(defaultProductResponseDtoList.get(1));
        Mockito.when(modelMapperMock.map(defaultProductEntityList.get(2), ProductResponseDto.class)).thenReturn(defaultProductResponseDtoList.get(2));

        List<ProductResponseDto> expectedProductResponseDto = defaultProductResponseDtoList;
        List<ProductResponseDto> actualProductResponseDto = productService.getAll();

        Assertions.assertEquals(expectedProductResponseDto, actualProductResponseDto);

    }

    @Test
    void givenValidId_whenGetById_ThenReturnProductRespenseDto(){

        Mockito.when(productRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(defaultProductEntity));

        Mockito.when(modelMapperMock.map(defaultProductEntity, ProductResponseDto.class)).thenReturn(defaultProductResponseDto);

        ProductResponseDto expectedProductResponseDto = defaultProductResponseDto;
        ProductResponseDto actualProductResponseDto = productService.getById(1L);

        Assertions.assertEquals(expectedProductResponseDto, actualProductResponseDto);

    }

    @Test
    void givenAnValidId_whenGetById_ThenThrowsEntityNotFoundException(){

        Mockito.when(productRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getById(1L));


    }

    @Test
    void givenValidProductUpdateDto_whenUpdateProduct_ThenReturnProductRespenseDto(){

        Mockito.when(productRepositoryMock.findById(1L)).thenReturn(Optional.of(defaultProductEntity));
        Mockito.when(productRepositoryMock.isNameExistsInProducts(defaultProductEntity.getName())).thenReturn(false);
        Mockito.when(productRepositoryMock.isCodeExistsInProducts(defaultProductEntity.getCode())).thenReturn(false);
        Mockito.when(productRepositoryMock.saveAndFlush(defaultProductEntity)).thenReturn(defaultProductEntity);
        Mockito.when(modelMapperMock.map(defaultProductEntity, ProductResponseDto.class)).thenReturn(defaultProductUpdateRespenseDto);

        ProductResponseDto expectedProductResponseDto = defaultProductUpdateRespenseDto;
        ProductResponseDto actualProductResponseDto = productService.update(1L, defaultProductUpdateDto);

        Assertions.assertEquals(expectedProductResponseDto.getId(), actualProductResponseDto.getId());
        Assertions.assertEquals(expectedProductResponseDto.getCode(), actualProductResponseDto.getCode());
        Assertions.assertEquals(expectedProductResponseDto.getName(), actualProductResponseDto.getName());
        Assertions.assertEquals(expectedProductResponseDto.getDescription(), actualProductResponseDto.getDescription());
        Assertions.assertEquals(expectedProductResponseDto.getPrice(), actualProductResponseDto.getPrice());
        Assertions.assertEquals(expectedProductResponseDto.getQuantity(), actualProductResponseDto.getQuantity());
        Assertions.assertEquals(expectedProductResponseDto.getInventoryStatus(), actualProductResponseDto.getInventoryStatus());
        Assertions.assertEquals(expectedProductResponseDto.getCategory(), actualProductResponseDto.getCategory());
        Assertions.assertEquals(expectedProductResponseDto.getImage(), actualProductResponseDto.getImage());
        Assertions.assertEquals(expectedProductResponseDto.getRating(), actualProductResponseDto.getRating());
    }
}
