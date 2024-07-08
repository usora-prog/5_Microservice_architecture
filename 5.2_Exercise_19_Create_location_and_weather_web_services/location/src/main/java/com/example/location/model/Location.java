package com.example.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull private String location;
    @NonNull private double latitude;
    @NonNull private double longitude;


    public Location(@NonNull String location, @NonNull double latitude, @NonNull double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}