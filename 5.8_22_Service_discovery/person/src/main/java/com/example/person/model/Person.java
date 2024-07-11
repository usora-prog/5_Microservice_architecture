package com.example.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull private String name;
    @NonNull private String surname;
    @NonNull private String lastname;
    @NonNull private LocalDate birthday;
    @NonNull private String location;

    public Person(@NonNull String name, @NonNull String surname, @NonNull String lastname, @NonNull LocalDate birthday, @NonNull String location) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.location = location;
    }
}