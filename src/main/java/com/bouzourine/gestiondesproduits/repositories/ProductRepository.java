package com.bouzourine.gestiondesproduits.repositories;

import com.bouzourine.gestiondesproduits.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p where p.code = :code")
    boolean isCodeExistsInProducts(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p where p.name = :name")
    boolean isNameExistsInProducts(@Param("name") String name);

}
