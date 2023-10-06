package com.bouzourine.gestiondesproduits.controllers;

import com.bouzourine.gestiondesproduits.dtos.product.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.product.ProductResponseDto;
import com.bouzourine.gestiondesproduits.dtos.product.ProductUpdateDto;
import com.bouzourine.gestiondesproduits.entities.Category;
import com.bouzourine.gestiondesproduits.entities.InventoryStatus;
import com.bouzourine.gestiondesproduits.exceptions.EntityAlreadyExistsException;
import com.bouzourine.gestiondesproduits.exceptions.EntityInvalidException;
import com.bouzourine.gestiondesproduits.exceptions.EntityNotFoundException;
import com.bouzourine.gestiondesproduits.services.ProductService;
import com.bouzourine.gestiondesproduits.services.RoleService;
import com.bouzourine.gestiondesproduits.services.UserService;
import com.bouzourine.gestiondesproduits.utils.ProductErrorCodes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = {ProductController.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productServiceMock;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private RoleService roleServiceMock;


    private final ProductResponseDto productResponseDto = ProductResponseDto.builder()
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

    private final ProductResponseDto productUpdateDto = ProductResponseDto.builder()
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

    private final List<ProductResponseDto> productResponseDtoList = Arrays.asList(
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


    private final EntityNotFoundException entityNotFoundException = new EntityNotFoundException(ProductErrorCodes.ENTITY_TYPE);

    private final EntityAlreadyExistsException entityAlreadyExistsException = new EntityAlreadyExistsException(
            ProductErrorCodes.ENTITY_TYPE,
            new ArrayList<>(
                    Arrays.asList(ProductErrorCodes.NAME_PRODUCT_ALREADY_EXISTS,
                            ProductErrorCodes.CODE_PRODUCT_ALREADY_EXISTS )
            )
    );
    private final EntityInvalidException entityInvalidException = new EntityInvalidException(
            ProductErrorCodes.ENTITY_TYPE,
            new ArrayList<>(
                    Arrays.asList(ProductErrorCodes.NAME_PRODUCT_ALREADY_EXISTS,
                            ProductErrorCodes.CODE_PRODUCT_ALREADY_EXISTS )
            )
    );

    @Test
    void createProduct_withValidProductCreationDto_ThenOk() throws Exception {
        String productCreationDto = """
                {
                  "code": "123456789",
                  "name": "nameTest",
                  "description": "description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "image.png",
                  "rating": 10
                }                
                """;

        Mockito.when(productServiceMock.create(any(ProductCreationDto.class)))
                .thenReturn(productResponseDto);

        mvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCreationDto)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(productResponseDto.getId().intValue())))
                .andExpect(jsonPath("$.code", equalTo(productResponseDto.getCode())))
                .andExpect(jsonPath("$.name", equalTo(productResponseDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(productResponseDto.getDescription())))
                .andExpect(jsonPath("$.price", equalTo(productResponseDto.getPrice())))
                .andExpect(jsonPath("$.quantity", equalTo(productResponseDto.getQuantity())))
                .andExpect(jsonPath("$.inventoryStatus", equalTo(productResponseDto.getInventoryStatus())))
                .andExpect(jsonPath("$.category", equalTo(productResponseDto.getCategory())))
                .andExpect(jsonPath("$.image", equalTo(productResponseDto.getImage())))
                .andExpect(jsonPath("$.rating", equalTo(productResponseDto.getRating())))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void createProduct_withCodeAndNameAlreadyExists_ThenisConflict() throws Exception {
        String productCreationDto = """
                {
                  "code": "AlreadyEx",
                  "name": "productAlreadyExists",
                  "description": "description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "image.png",
                  "rating": 10
                }                
                """;
        Mockito.when(productServiceMock.create(any(ProductCreationDto.class)))
                .thenThrow(entityAlreadyExistsException);

        mvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCreationDto)
                )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(409)))
                .andExpect(jsonPath("$.message", equalTo("ETITY_product_ALREADY_EXIST")))
                .andExpect(jsonPath("$.errors[0]", equalTo("name already exists.")))
                .andExpect(jsonPath("$.errors[1]", equalTo("code already exists.")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void createProduct_withInvalidProduct_ThenisBadRequest() throws Exception {
        String productCreationDto = """
                {
                  "code": "",
                  "name": "",
                  "description": "",
                  "price": -10,
                  "quantity": -100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "image.png",
                  "rating": -10
                }                
                """;

        mvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCreationDto)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("ETITY_ProductCreationDto_CONTAINS_INVALID_FIELDS")))
                .andExpect(jsonPath("$.errors", isA(List.class)))
                .andExpect(jsonPath("$.errors", hasSize(7)))
                .andExpect(jsonPath("$.errors[*]", everyItem(isA(String.class))))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void createProduct_withInvalidInventoryStatusAndInvalidCategory_ThenisBadRequest() throws Exception {
        String productCreationDto = """
                {
                  "code": "123456789",
                  "name": "productAlreadyExists",
                  "description": "description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "invalidInventoryStatus",
                  "category": "invalidCategory",
                  "image": "image.png",
                  "rating": 10
                }                
                """;

        mvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productCreationDto)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("JSON_PARSE_ERROR")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void getAll_ThenOk() throws Exception {
        Mockito.when(productServiceMock.getAll()).thenReturn(productResponseDtoList);

        mvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", equalTo(productResponseDtoList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].code", equalTo(productResponseDtoList.get(0).getCode())))
                .andExpect(jsonPath("$[0].name", equalTo(productResponseDtoList.get(0).getName())))
                .andExpect(jsonPath("$[0].description", equalTo(productResponseDtoList.get(0).getDescription())))
                .andExpect(jsonPath("$[0].price", equalTo(productResponseDtoList.get(0).getPrice())))
                .andExpect(jsonPath("$[0].quantity", equalTo(productResponseDtoList.get(0).getQuantity())))
                .andExpect(jsonPath("$[0].inventoryStatus", equalTo(productResponseDtoList.get(0).getInventoryStatus())))
                .andExpect(jsonPath("$[0].category", equalTo(productResponseDtoList.get(0).getCategory())))
                .andExpect(jsonPath("$[0].image", equalTo(productResponseDtoList.get(0).getImage())))
                .andExpect(jsonPath("$[0].rating", equalTo(productResponseDtoList.get(0).getRating())))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }



    @Test
    void getProduct_withValidId_ThenIsOk() throws Exception {

        Mockito.when(productServiceMock.getById(1L)).thenReturn(productResponseDto);

        mvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(productResponseDto.getId().intValue())))
                .andExpect(jsonPath("$.code", equalTo(productResponseDto.getCode())))
                .andExpect(jsonPath("$.name", equalTo(productResponseDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(productResponseDto.getDescription())))
                .andExpect(jsonPath("$.price", equalTo(productResponseDto.getPrice())))
                .andExpect(jsonPath("$.quantity", equalTo(productResponseDto.getQuantity())))
                .andExpect(jsonPath("$.inventoryStatus", equalTo(productResponseDto.getInventoryStatus())))
                .andExpect(jsonPath("$.category", equalTo(productResponseDto.getCategory())))
                .andExpect(jsonPath("$.image", equalTo(productResponseDto.getImage())))
                .andExpect(jsonPath("$.rating", equalTo(productResponseDto.getRating())))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void getProductById_withAnValidId_ThenIsBadRequest() throws Exception {
        Mockito.when(productServiceMock.getById(-1L)).thenThrow(entityNotFoundException);

        mvc.perform(MockMvcRequestBuilders.get("/products/{id}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo("ETITY_product_NOT_FOUND")))
                .andExpect(jsonPath("$.errors", equalTo(null)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateProduct_withValidProductUpdateDto_ThenOk() throws Exception {
        Long idProductToUpdate = 1L;
        String productToUpdateDto = """
                {
                  "code": "123456789",
                  "name": "new name",
                  "description": "new description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "new image.png",
                  "rating": 10
                }                
                """;

        Mockito.when(productServiceMock.update(any(Long.class), any(ProductUpdateDto.class)))
                .thenReturn(productUpdateDto);

        mvc.perform(MockMvcRequestBuilders.patch("/products/{id}", idProductToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToUpdateDto)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(productUpdateDto.getId().intValue())))
                .andExpect(jsonPath("$.code", equalTo(productUpdateDto.getCode())))
                .andExpect(jsonPath("$.name", equalTo(productUpdateDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(productUpdateDto.getDescription())))
                .andExpect(jsonPath("$.price", equalTo(productUpdateDto.getPrice())))
                .andExpect(jsonPath("$.quantity", equalTo(productUpdateDto.getQuantity())))
                .andExpect(jsonPath("$.inventoryStatus", equalTo(productUpdateDto.getInventoryStatus())))
                .andExpect(jsonPath("$.category", equalTo(productUpdateDto.getCategory())))
                .andExpect(jsonPath("$.image", equalTo(productUpdateDto.getImage())))
                .andExpect(jsonPath("$.rating", equalTo(productUpdateDto.getRating())))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateProduct_withCodeAndNameAlreadyExists_ThenisBadRequest() throws Exception {
        Long idProductToUpdate = 1L;
        String productUpdateDtoDto = """
                {
                  "code": "AlreadyEx",
                  "name": "nameProductAlreadyExists",
                  "description": "description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "image.png",
                  "rating": 10
                }                
                """;
        Mockito.when(productServiceMock.update(any(Long.class),any(ProductUpdateDto.class)))
                .thenThrow(entityInvalidException);

        mvc.perform(MockMvcRequestBuilders.patch("/products/{id}",idProductToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productUpdateDtoDto)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("ETITY_product_INVALID")))
                .andExpect(jsonPath("$.errors[0]", equalTo("name already exists.")))
                .andExpect(jsonPath("$.errors[1]", equalTo("code already exists.")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateProduct_withInvalidProduct_ThenisBadRequest() throws Exception {
        Long idProductToUpdate = 1L;
        String productUpdateDto = """
                {
                  "code": "",
                  "name": "",
                  "description": "",
                  "price": -10,
                  "quantity": -100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "image.png",
                  "rating": -10
                }                
                """;

        mvc.perform(MockMvcRequestBuilders.patch("/products/{id}", idProductToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productUpdateDto)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("ETITY_ProductUpdateDto_CONTAINS_INVALID_FIELDS")))
                .andExpect(jsonPath("$.errors", isA(List.class)))
                .andExpect(jsonPath("$.errors", hasSize(7)))
                .andExpect(jsonPath("$.errors[*]", everyItem(isA(String.class))))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateProduct_withInvalidInventoryStatusAndInvalidCategory_ThenisBadRequest() throws Exception {
        Long idProductToUpdate = 1L;
        String productUpdateDtoDto = """
                {
                  "code": "123456789",
                  "name": "productAlreadyExists",
                  "description": "description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "invalidInventoryStatus",
                  "category": "invalidCategory",
                  "image": "image.png",
                  "rating": 10
                }                
                """;

        mvc.perform(MockMvcRequestBuilders.patch("/products/{id}", idProductToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productUpdateDtoDto)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("JSON_PARSE_ERROR")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateProduct_withInvalidId_ThenisNotFound() throws Exception {
        String productUpdateDtoDto = """
                {
                  "code": "123456789",
                  "name": "productAlreadyExists",
                  "description": "description",
                  "price": 10,
                  "quantity": 100,
                  "inventoryStatus": "INSTOCK",
                  "category": "Accessories",
                  "image": "image.png",
                  "rating": 10
                }                
                """;
        Mockito.when(productServiceMock.update(any(Long.class), any(ProductUpdateDto.class))).thenThrow(entityNotFoundException);

        mvc.perform(MockMvcRequestBuilders.patch("/products/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productUpdateDtoDto)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpCode", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo("ETITY_product_NOT_FOUND")))
                .andExpect(jsonPath("$.errors", equalTo(null)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteProduct_ThenIsNoContent() throws Exception {
        Long productId = 1L;
        doNothing().when(productServiceMock).delete(productId);
        mvc.perform(delete("/products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(productServiceMock, Mockito.times(1)).delete(1L);
    }




}
