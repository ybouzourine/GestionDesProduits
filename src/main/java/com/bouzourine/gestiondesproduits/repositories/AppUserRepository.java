package com.bouzourine.gestiondesproduits.repositories;

import com.bouzourine.gestiondesproduits.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM AppUser u where u.username = :username")
    boolean isUsernameExistsInUsers(@Param("username") String username);

    @Query("SELECT u FROM AppUser u where u.username = :username")
    Optional<AppUser> findByUsername(String username);

}
