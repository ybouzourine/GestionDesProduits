package com.bouzourine.gestiondesproduits.repositories;

import com.bouzourine.gestiondesproduits.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u where u.username = :username")
    boolean isUsernameExistsInUsers(@Param("username") String username);

    @Query("SELECT u FROM User u where u.username = :username")
    Optional<User> findByUsername(String username);

}
