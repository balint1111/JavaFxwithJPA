package com.example.spring_javafx.service;


import com.example.spring_javafx.entities.PetEntity;
import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service("personService")
public class PersonService {
    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }


    public PersonEntity save(PersonEntity entity){
        return repository.save(entity);
    }

    public PersonEntity getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<PersonEntity> getPersons() {
        return repository.findAll();
    }

    public List<PersonEntity> saveAll(List<PersonEntity> entities) {
        return repository.saveAll(entities);
    }

    public List<PetEntity> getAnimals(){
        return new ArrayList<>();
    }



}
