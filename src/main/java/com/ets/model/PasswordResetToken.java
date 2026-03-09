//package com.ets.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.Instant;
//
//@Setter
//@Getter
//@Entity
//@Table(name = "password_reset_tokens")
//public class PasswordResetToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String token;
//
//    private Instant expiresAt;
//
//    private boolean used = false;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private AdminLoginUser user;
//}


package com.ets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(
    name = "password_reset_tokens",
    indexes = {
        @Index(name = "idx_reset_token_token", columnList = "token", unique = true),
        @Index(name = "idx_reset_token_user", columnList = "user_id")
    }
)
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AdminLoginUser user;
}