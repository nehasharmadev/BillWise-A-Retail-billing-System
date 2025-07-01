package com.example.Billing.System.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
}

