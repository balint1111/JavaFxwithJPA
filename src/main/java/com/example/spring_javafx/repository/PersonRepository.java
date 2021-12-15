package com.example.spring_javafx.repository;


import com.example.spring_javafx.entities.GenderEnum;
import com.example.spring_javafx.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findAll();

    List<PersonEntity> findAllByGender(GenderEnum gender);

    //JPA QUERY
    @Query("select p from PersonEntity p where p.age between ?1 and ?2")
    List<PersonEntity> findAllByAgeBetween(Integer from, Integer To);

    //native SQL Query
    @Query(value = "select p from person p where p.age between ?1 and ?2",nativeQuery = true)
    List<PersonEntity> findAllByAgeBetweenNative(Integer from, Integer To);
}
