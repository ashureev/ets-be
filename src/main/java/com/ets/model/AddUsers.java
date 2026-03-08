package com.ets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "add_users")
public class AddUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_username", nullable = false)
    private String nameUsername;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "access_password", nullable = false)
    private String accessPassword;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "dept", nullable = false)
    private String dept;

}