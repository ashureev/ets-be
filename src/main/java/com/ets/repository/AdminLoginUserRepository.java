package com.ets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.AdminLoginUser;

@Repository
public interface AdminLoginUserRepository extends JpaRepository<AdminLoginUser, Long> {

    Optional<AdminLoginUser> findByUsername(String username);

    Optional<AdminLoginUser> findByEmail(String email);
}