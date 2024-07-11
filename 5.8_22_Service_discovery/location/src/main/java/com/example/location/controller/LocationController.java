package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RestController
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/locations")
    public Iterable<Location> getLocations() {
        return repository.findAll();
    }

    @GetMapping("location")
    public Optional<Location> getLocationByName(@RequestParam String name) {
        Optional<Location> optionalGeodata = repository.findByName(name);
        if (optionalGeodata.isPresent()) {
            Location location = optionalGeodata.get();
            return Optional.of(location);
        } else {
            return Optional.empty();
        }

    }

    @PostMapping("/location")
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping("/location")
    public ResponseEntity<Location> update(@RequestParam String name, @RequestBody Location locationNew) {
        Optional<Location> optionalGeodata = repository.findByName(name);
        if (optionalGeodata.isPresent()) {
            Location location = optionalGeodata.get();
            locationNew.setId(location.getId());
            return new ResponseEntity<>(repository.save(locationNew), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(save(locationNew), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("location")
    public void delete(@RequestParam String name) {
        Location location = repository.findByName(name).get();
        repository.delete(location);
    }

    @GetMapping("location/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        Optional<Location> optionalGeodata = repository.findByName(name);
        if (optionalGeodata.isPresent()) {
            Location location = optionalGeodata.get();
            String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s", location.getLat(), location.getLon());
            return restTemplate.getForObject(url, Weather.class);
        } else {
            throw new RuntimeException("Geodata not found for name: " + name);
        }
    }
}