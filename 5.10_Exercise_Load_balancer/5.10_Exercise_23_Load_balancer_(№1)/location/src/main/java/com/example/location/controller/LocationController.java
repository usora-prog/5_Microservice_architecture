package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${weather.url}")
    String weatherUrl;

    // Получить все List<Location>
    @GetMapping("/locations")
    public Iterable<Location> getLocations() {
        return repository.findAll();
    }

    // Получить Location по name
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

    // Добавить новый Location
    @PostMapping("/location")
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    // Изменить Location по name
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

    // Удалить Location по name
    @DeleteMapping("location")
    public void delete(@RequestParam String name) {
        Location location = repository.findByName(name).get();
        repository.delete(location);
    }

    // Получить погоду для Location по name
    @GetMapping("location/weather")
    public Weather redirectRequestWeather(@RequestParam String name) {
        Optional<Location> optionalGeodata = repository.findByName(name);
        if (optionalGeodata.isPresent()) {
            Location location = optionalGeodata.get();
            String url = String.format("http://%s/weather?lat=%s&lon=%s", weatherUrl, location.getLat(), location.getLon());
            return restTemplate.getForObject(url, Weather.class);
        } else {
            throw new RuntimeException("Location not found for name: " + name);
        }
    }
}
