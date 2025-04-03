package com.travel.compass.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel_packages")
public class HotelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int bedCount;
    private double price;
    private boolean available;

    // Constructors
    public HotelPackage() {}

    public HotelPackage(String name, String description, int bedCount, double price, boolean available) {
        this.name = name;
        this.description = description;
        this.bedCount = bedCount;
        this.price = price;
        this.available = available;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getBedCount() { return bedCount; }
    public void setBedCount(int bedCount) { this.bedCount = bedCount; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
