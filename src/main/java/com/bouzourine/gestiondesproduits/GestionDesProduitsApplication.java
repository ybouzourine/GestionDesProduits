package com.bouzourine.gestiondesproduits;

import com.bouzourine.gestiondesproduits.dtos.ProductCreationDto;
import com.bouzourine.gestiondesproduits.dtos.RoleCreationDto;
import com.bouzourine.gestiondesproduits.dtos.RoleUserForm;
import com.bouzourine.gestiondesproduits.dtos.UserCreationDto;
import com.bouzourine.gestiondesproduits.entities.Category;
import com.bouzourine.gestiondesproduits.entities.InventoryStatus;
import com.bouzourine.gestiondesproduits.services.ProductService;
import com.bouzourine.gestiondesproduits.services.RoleService;
import com.bouzourine.gestiondesproduits.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GestionDesProduitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDesProduitsApplication.class, args);
	}


	@Bean
	CommandLineRunner startCreateProduct(ProductService productService, UserService userService, RoleService roleService){
		return args -> {
			userService.create(UserCreationDto.builder()
					.username("admin")
					.password("123")
					.build());
			userService.create(UserCreationDto.builder()
					.username("user")
					.password("123")
					.build());

			roleService.create(RoleCreationDto.builder()
					.roleName("ADMIN")
					.build());
			roleService.create(RoleCreationDto.builder()
					.roleName("USER")
					.build());

			userService.addRoleToUser(RoleUserForm.builder()
					.roleName("ADMIN")
					.username("admin")
					.build());
			userService.addRoleToUser(RoleUserForm.builder()
					.roleName("USER")
					.username("admin")
					.build());
			userService.addRoleToUser(RoleUserForm.builder()
					.roleName("USER")
					.username("user")
					.build());

			productService.create(ProductCreationDto.builder()
					.code("f230fh0g3")
					.name("Bamboo Watch")
					.description("Product Description")
					.image("bamboo-watch.jpg")
					.price(65)
					.category(Category.Accessories)
					.quantity(24)
					.inventoryStatus(InventoryStatus.INSTOCK)
					.rating(5)
					.build());
			productService.create(ProductCreationDto.builder()
					.code("nvklal433")
					.name("Black Watch")
					.description("Product Description")
					.image("black-watch.jpg")
					.price(72)
					.category(Category.Accessories)
					.quantity(61)
					.inventoryStatus(InventoryStatus.INSTOCK)
					.rating(4)
					.build());
			productService.create(ProductCreationDto.builder()
					.code("zz21cz3c1")
					.name("Blue Band")
					.description("Product Description")
					.image("blue-band.jpg")
					.price(79)
					.category(Category.Fitness)
					.quantity(2)
					.inventoryStatus(InventoryStatus.LOWSTOCK)
					.rating(3)
					.build());
			productService.create(ProductCreationDto.builder()
					.code("244wgerg2")
					.name("Blue T-Shirt")
					.description("Product Description")
					.image("blue-t-shirt.jpg")
					.price(29)
					.category(Category.Clothing)
					.quantity(25)
					.inventoryStatus(InventoryStatus.INSTOCK)
					.rating(5)
					.build());
			productService.create(ProductCreationDto.builder()
					.code("h456wer53")
					.name("Bracelet")
					.description("Product Description")
					.image("bracelet.jpg")
					.price(15)
					.category(Category.Accessories)
					.quantity(73)
					.inventoryStatus(InventoryStatus.INSTOCK)
					.rating(4)
					.build());
		};
	}

}
