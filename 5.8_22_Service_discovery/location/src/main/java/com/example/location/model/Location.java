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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    private double lon;
    @NonNull private double lat;
    @NonNull private String name;
}