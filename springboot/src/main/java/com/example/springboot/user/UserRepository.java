package com.example.springboot.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserDomain, String> {
    @EntityGraph(attributePaths = {"userRoleList"})
    @Query("select u from UserDomain u where u.email = :email")
    UserDomain getWithRoles(@Param("email") String email);
}
