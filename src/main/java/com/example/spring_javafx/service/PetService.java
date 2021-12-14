package com.example.spring_javafx.service;

import com.example.spring_javafx.entities.PetEntity;
import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("animalService")
public class PetService {
    private PetRepository repository;

    @Autowired
    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public PetEntity save(PetEntity entity) {
        return repository.save(entity);
    }

    public PetEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public PersonEntity getOwner(Long id) {
        return repository.getById(id).getOwner();
    }


}
