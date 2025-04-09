package com.travel.compass.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "guides")
@Data
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String licenseNumber;
    private String areasOfExpertise;
    private String languagesSpoken;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isVerified = false;
}