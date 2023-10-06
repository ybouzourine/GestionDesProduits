package com.bouzourine.gestiondesproduits.repositories;

import com.bouzourine.gestiondesproduits.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Role r where r.roleName = :roleName")
    boolean isRoleNameExistsInRoles(@Param("roleName") String roleName);

    @Query("SELECT r FROM Role r where r.roleName = :roleName")
    Optional<Role> findByRoleName(String roleName);
}
