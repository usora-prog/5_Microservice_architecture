package com.example.weather.model;

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
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private double latitude;
    @NonNull
    private double longitude;
    @NonNull
    private double temperature;
    @NonNull
    private double humidity;
    @NonNull
    private double wind_speed;

    public Weather(double latitude, double longitude, double temperature, double humidity, double wind_speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
    }
}
