package com.ets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.AddUsers;

@Repository
public interface AddUsersRepository extends JpaRepository<AddUsers, Long> {
	
    boolean existsByEmailAddress(String emailAddress);

    Optional<AddUsers> findByEmailAddress(String emailAddress);

}
