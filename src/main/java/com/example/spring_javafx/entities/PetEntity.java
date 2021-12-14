package com.example.spring_javafx.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animal")
public class PetEntity {

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
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private PersonEntity owner;
}
