package com.ets.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
	
