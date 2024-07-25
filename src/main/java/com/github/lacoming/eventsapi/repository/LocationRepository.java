package com.github.lacoming.eventsapi.repository;

import com.github.lacoming.eventsapi.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByCityAndStreet(String city, String street);

}
