package com.example.spring_javafx.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class PersonEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "tars_id", referencedColumnName = "id")
    private PersonEntity tars;

//    @OneToMany(mappedBy = "owner")
//    private List<AnimalEntity> animals;


}