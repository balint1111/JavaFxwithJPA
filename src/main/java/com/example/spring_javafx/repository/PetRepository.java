package com.example.spring_javafx.repository;

import com.example.spring_javafx.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long> {


}