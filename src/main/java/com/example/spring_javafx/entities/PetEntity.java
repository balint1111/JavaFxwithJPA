package com.example.spring_javafx.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pet")
public class PetEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "species")
    private SpeciesEnum species;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private PersonEntity owner;
}
