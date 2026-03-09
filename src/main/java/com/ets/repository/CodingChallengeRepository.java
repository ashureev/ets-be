package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.CodingChallenge;

@Repository
public interface CodingChallengeRepository extends JpaRepository<CodingChallenge, Long> {

}
