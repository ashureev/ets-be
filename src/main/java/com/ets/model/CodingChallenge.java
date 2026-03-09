package com.ets.model;


import com.ets.enums.ChallengeStatus;
import com.ets.enums.DifficultyLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coding_challenges")
public class CodingChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

   
    private String description;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private String solveUrl;

}