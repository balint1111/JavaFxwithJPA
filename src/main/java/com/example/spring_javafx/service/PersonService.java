package com.example.spring_javafx.service;


import com.example.spring_javafx.entities.GenderEnum;
import com.example.spring_javafx.entities.PetEntity;
import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
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


    static final String JDBC_DRIVER = "org.h2.Driver";
    @Value("${spring.datasource.url}")
    private String DB_URL;

    //  Database credentials
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASS;

    public List<PersonEntity> jdbcGetPersons(){
        Connection conn = null;
        Statement stmt = null;

        List<PersonEntity> ret = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            stmt = conn.createStatement();
            String sql =  "SELECT * FROM person";

            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                GenderEnum gender = GenderEnum.valueOf(resultSet.getString("gender"));
                ret.add(new PersonEntity(id,name,age,gender));
            }

            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @PersistenceContext
    private EntityManager em;

    public List<PersonEntity> criteriaGetPersons(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> cq = cb.createQuery(PersonEntity.class);

        Root<PersonEntity> person = cq.from(PersonEntity.class);
        cq.select(person);

        TypedQuery<PersonEntity> query = em.createQuery(cq);
        return query.getResultList();
    }



}
