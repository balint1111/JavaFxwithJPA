package com.example.spring_javafx.service;


import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
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



    @PostConstruct
    public void init() {
        PersonEntity person = new PersonEntity(null,"Egyik", 25,null);
        PersonEntity savedPerson = save(person);
        PersonEntity person2 = new PersonEntity(null,"Masik", 30, savedPerson);
        PersonEntity savedPerson2 = save(person2);

        System.out.println(getById(1L));
    }
}
