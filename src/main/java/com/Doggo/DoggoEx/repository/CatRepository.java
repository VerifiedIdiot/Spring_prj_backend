package com.Doggo.DoggoEx.repository;

import com.Doggo.DoggoEx.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
}
