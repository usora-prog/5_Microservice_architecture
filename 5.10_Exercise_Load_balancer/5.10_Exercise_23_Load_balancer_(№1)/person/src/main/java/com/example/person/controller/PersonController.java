package com.example.person.controller;

import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${location.url}")
    String url;

    // Получить всех пользователей List<Person>
    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    // Получить пользователя Person по id
    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }

    // Добавить нового пользователя Person
    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

    // Изменить пользователя Person по id
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {

        if (repository.existsById(id)) {
            person.setId(id);
            return new ResponseEntity<>(repository.save(person), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(repository.save(person), HttpStatus.CREATED);
        }
    }

    // Удалить пользователя Person по id
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        repository.deleteById(id);
    }


    // Получить погоду для местонахождения пользователя Person по id
    @GetMapping("{id}/weather")
    public ResponseEntity<Weather> getWeather(@PathVariable int id) {
        if (repository.existsById(id)) {
            String location = repository.findById(id).get().getLocation();
            Weather weather = restTemplate.getForObject(url + location, Weather.class);
            return new ResponseEntity(weather, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}
