package com.example.spring_javafx.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pet")
public class PetEntity {

    public PetEntity(Long id, String name, Integer age, SpeciesEnum species, PersonEntity owner) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.species = species;
        this.owner = owner;
        ownerIdentity = owner.getId();
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

//    @Column(name = "breed")
//    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "species")
    private SpeciesEnum species;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private PersonEntity owner;

    private Long ownerIdentity;
}
