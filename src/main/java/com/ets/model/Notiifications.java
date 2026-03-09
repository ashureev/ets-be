package com.ets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "notiifications")
public class Notiifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    private String type; // INFO, WARNING, ALERT, etc.

    private boolean isRead = false;

    private LocalDateTime createdAt;

}