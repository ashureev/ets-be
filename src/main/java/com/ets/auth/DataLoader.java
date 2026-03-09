package com.ets.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ets.enums.ChallengeStatus;
import com.ets.enums.DifficultyLevel;
import com.ets.model.CodingChallenge;
import com.ets.repository.CodingChallengeRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CodingChallengeRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                CodingChallenge twoSum = new CodingChallenge();
                twoSum.setTitle("Two Sum");
                twoSum.setDescription("Write a function to solve the Two Sum problem efficiently.");
                twoSum.setDifficulty(DifficultyLevel.EASY);
                twoSum.setStatus(ChallengeStatus.SOLVED);
                twoSum.setSolveUrl("https://leetcode.com/problems/two-sum/");

                CodingChallenge reverseLinkedList = new CodingChallenge();
                reverseLinkedList.setTitle("Reverse Linked List");
                reverseLinkedList.setDescription("Write a function to solve the Reverse Linked List problem efficiently.");
                reverseLinkedList.setDifficulty(DifficultyLevel.MEDIUM);
                reverseLinkedList.setStatus(ChallengeStatus.PENDING);
                reverseLinkedList.setSolveUrl("https://leetcode.com/problems/reverse-linked-list/");

                CodingChallenge validPalindrome = new CodingChallenge();
                validPalindrome.setTitle("Valid Palindrome");
                validPalindrome.setDescription("Write a function to solve the Valid Palindrome problem efficiently.");
                validPalindrome.setDifficulty(DifficultyLevel.EASY);
                validPalindrome.setStatus(ChallengeStatus.PENDING);
                validPalindrome.setSolveUrl("https://leetcode.com/problems/valid-palindrome/");

                repository.save(twoSum);
                repository.save(reverseLinkedList);
                repository.save(validPalindrome);
            }
        };
    }
}