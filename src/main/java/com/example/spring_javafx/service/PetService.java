package com.example.spring_javafx.service;

import com.example.spring_javafx.entities.GenderEnum;
import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.entities.PetEntity;
import com.example.spring_javafx.entities.SpeciesEnum;
import com.example.spring_javafx.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service("petService")
public class PetService {
    private PetRepository repository;

    @Autowired
    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public PetEntity save(PetEntity entity) {
        return repository.save(entity);
    }

    public List<PetEntity> saveAll(List<PetEntity> entities) {
        return repository.saveAll(entities);
    }

    public void delete(PetEntity pet) {
        repository.delete(pet);
    }

    public PetEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<PetEntity> getAllByOwner(PersonEntity owner) {
        return repository.findAllByOwner(owner);
    }

    public List<PetEntity> getAllByNameContaining(String name) {
        return repository.findAllByNameContaining(name);
    }

    public List<PetEntity> getPets() {
        return repository.findAll();
    }

    static final String JDBC_DRIVER = "org.h2.Driver";
    @Value("${spring.datasource.url}")
    private String DB_URL;

    //  Database credentials
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASS;

    public List<PetEntity> jdbcGetPets() {
        Connection conn = null;
        Statement stmt = null;

        List<PetEntity> ret = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql = "SELECT * FROM pet";

            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                SpeciesEnum species = SpeciesEnum.valueOf(resultSet.getString("species"));
                Long ownerId = resultSet.getLong("owner_id");

                PersonEntity owner = null;

                Statement stmt2 = conn.createStatement();
                String sql2 = "SELECT * FROM person where id=" + ownerId;
                ResultSet resultSet2 = stmt2.executeQuery(sql2);
                while (resultSet2.next()) {
                    Long personId = resultSet2.getLong("id");
                    String personName = resultSet2.getString("name");
                    Integer personAge = resultSet2.getInt("age");
                    GenderEnum gender = GenderEnum.valueOf(resultSet2.getString("gender"));
                    owner = new PersonEntity(personId, personName, personAge, gender);
                }

                ret.add(new PetEntity(id, name, age, species, owner));
            }

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @PersistenceContext
    private EntityManager em;

    public List<PetEntity> criteriaGetPets() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PetEntity> cq = cb.createQuery(PetEntity.class);

        Root<PetEntity> pet = cq.from(PetEntity.class);
        cq.select(pet);

        TypedQuery<PetEntity> query = em.createQuery(cq);
        return query.getResultList();
    }

    public PersonEntity getOwner(Long id) {
        return repository.getById(id).getOwner();
    }


}
