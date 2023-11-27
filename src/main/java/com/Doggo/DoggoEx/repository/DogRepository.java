package com.Doggo.DoggoEx.repository;

import com.Doggo.DoggoEx.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findByName(String name);
}
