package com.ets.controller;


import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.CodingChallenge;
import com.ets.service.CodingChallengeService;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin(origins = "*")
public class CodingChallengeController {

    private final CodingChallengeService codingChallengeService;

    public CodingChallengeController(CodingChallengeService codingChallengeService) {
        this.codingChallengeService = codingChallengeService;
    }

    @GetMapping
    public ResponseEntity<List<CodingChallenge>> getAllChallenges() {
        return ResponseEntity.ok(codingChallengeService.getAllChallenges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodingChallenge> getChallengeById(@PathVariable Long id) {
        return ResponseEntity.ok(codingChallengeService.getChallengeById(id));
    }

    @PostMapping
    public ResponseEntity<CodingChallenge> createChallenge(@RequestBody CodingChallenge challenge) {
        return ResponseEntity.ok(codingChallengeService.saveChallenge(challenge));
    }

    @GetMapping("/{id}/solve-url")
    public ResponseEntity<Map<String, String>> getSolveUrl(@PathVariable Long id) {
        String url = codingChallengeService.getSolveUrlById(id);
        Map<String, String> response = new HashMap<>();
        response.put("solveUrl", url);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/solve")
    public ResponseEntity<Void> redirectToSolveUrl(@PathVariable Long id) {
        String url = codingChallengeService.getSolveUrlById(id);
        return ResponseEntity.status(302)
                .location(URI.create(url))
                .build();
    }
}
