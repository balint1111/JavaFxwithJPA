package com.example.spring_javafx;

import com.example.spring_javafx.entities.GenderEnum;
import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.entities.PetEntity;
import com.example.spring_javafx.entities.SpeciesEnum;
import com.example.spring_javafx.service.PersonService;
import com.example.spring_javafx.service.PetService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PrimarySceneController implements Initializable {

    @Autowired
    private PersonService personService;

    @Autowired
    private PetService petService;

    List<PersonEntity> persons;

    List<PetEntity> pets;


    @FXML
    public TableView<PersonEntity> personTable;

    @FXML
    public TableView<PetEntity> petTable;



    @FXML
    public TextField personNameField;

    @FXML
    public TextField personAgeField;

    @FXML
    ComboBox<GenderEnum> personGenderField;

    @FXML
    public TextField petNameField;

    @FXML
    public TextField petAgeField;

    @FXML
    ComboBox<SpeciesEnum> petSpeciesField;

    @FXML
    ComboBox<PersonEntity> petOwnerField;

    @FXML
    ComboBox<GenderEnum> personGenderSelect;

    @FXML
    ComboBox<PersonEntity> petOwnerSelect;

    @FXML
    public TextField petNameSelect;



    @FXML
    private void personLoadAction(ActionEvent event) {
        personsLoad();
    }

    @FXML
    private void petLoadAction(ActionEvent event) {
        petLoad();
    }


    @FXML
    private void selectByGender(ActionEvent event) {
        persons = personService.getAllByGender(personGenderSelect.getValue());
        personTable.getItems().setAll(persons);
    }


    @FXML
    private void selectByName(ActionEvent event) {
        pets = petService.getAllByNameContaining(petNameSelect.getText());
        petTable.getItems().setAll(pets);
    }


    @FXML
    private void selectByOwner(ActionEvent event) {
        pets = petService.getAllByOwner(petOwnerSelect.getValue());
        petTable.getItems().setAll(pets);
    }

    @FXML
    private void addPerson(ActionEvent event) {
        String name = personNameField.getText();
        Integer age = Integer.parseInt(personAgeField.getText());
        GenderEnum gender = personGenderField.getValue();
        PersonEntity entity = new PersonEntity(null,name,age,gender);
        personService.save(entity);
        personsLoad();
    }

    private void personsLoad() {
        persons = personService.getPersons();

        petOwnerField.setItems(FXCollections.observableArrayList(persons));
        petOwnerSelect.setItems(FXCollections.observableArrayList(persons));

        personTable.getItems().setAll(persons);
    }

    @FXML
    private void addPet(ActionEvent event) {
        String name = petNameField.getText();
        Integer age = Integer.parseInt(petAgeField.getText());
        SpeciesEnum species = petSpeciesField.getValue();
        PersonEntity owner = petOwnerField.getValue();
        PetEntity pet = new PetEntity(null,name,age,species,owner);
        petService.save(pet);
        petLoad();
    }

    private void petLoad() {
        pets = petService.getPets();
        petTable.getItems().setAll(pets);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personGenderField.setItems(FXCollections.observableArrayList(GenderEnum.values()));
        petSpeciesField.setItems(FXCollections.observableArrayList(SpeciesEnum.values()));
        personGenderSelect.setItems(FXCollections.observableArrayList(GenderEnum.values()));


        tableInit();

    }

    private void tableInit(){

        petLoad();
        personsLoad();
    }

    @PostConstruct
    public void init() {
        PersonEntity person = new PersonEntity(null,"Györffy Bálint", 21,GenderEnum.MAN);
        PersonEntity savedPerson = personService.save(person);
        PersonEntity person2 = new PersonEntity(null,"Valaki", 30, GenderEnum.MAN);
        PersonEntity savedPerson2 = personService.save(person2);

        PetEntity pet = new PetEntity(null,"rexi",4, SpeciesEnum.DOG,savedPerson);
        petService.save(pet);

        System.out.println(personService.getById(1L));
        System.out.println(petService.getById(1L));
        System.out.println("---------");
        System.out.println(petService.getPets());
        System.out.println("---------");
        System.out.println(petService.criteriaGetPets());
        System.out.println("---------");
        System.out.println(petService.jdbcGetPersons());
    }
}
