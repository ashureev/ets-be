package com.ets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ets.model.CodingChallenge;
import com.ets.repository.CodingChallengeRepository;

@Service
public class CodingChallengeService {

    private final CodingChallengeRepository codingChallengeRepository;

    public CodingChallengeService(CodingChallengeRepository codingChallengeRepository) {
        this.codingChallengeRepository = codingChallengeRepository;
    }

    public List<CodingChallenge> getAllChallenges() {
        return codingChallengeRepository.findAll();
    }

    public CodingChallenge getChallengeById(Long id) {
        return codingChallengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge not found with id: " + id));
    }

    public String getSolveUrlById(Long id) {
        CodingChallenge challenge = getChallengeById(id);
        return challenge.getSolveUrl();
    }

    public CodingChallenge saveChallenge(CodingChallenge challenge) {
        return codingChallengeRepository.save(challenge);
    }
}
