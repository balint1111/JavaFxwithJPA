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
import org.springframework.dao.DataIntegrityViolationException;
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
    public TextField personNameSelect;

    @FXML
    ComboBox<PetEntity> petSelect;

    @FXML
    ComboBox<PersonEntity> personSelect;


    //Személyek betöltése (Összes tárolt)
    @FXML
    private void personLoadAction(ActionEvent event) {
        personsLoad();
    }

    //Háziállatok betöltése (Összes tárolt)
    @FXML
    private void petLoadAction(ActionEvent event) {
        petLoad();
    }

    //Nem alapján lekérdezés
    @FXML
    private void selectByGender(ActionEvent event) {
        persons = personService.getAllByGender(personGenderSelect.getValue());
        personTable.getItems().setAll(persons);
    }

    //Név alapján lekérdezés(személy)
    @FXML
    private void selectPersonByName(ActionEvent event) {
        persons = personService.getAllByNameContaining(personNameSelect.getText());
        personTable.getItems().setAll(persons);
    }

    //személy törlése
    @FXML
    private void deletePerson(ActionEvent event) {
        try {
            personService.delete(personSelect.getValue());
        } catch (DataIntegrityViolationException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nem törölhető, mert egy háziállathoz már hozzá van rendelve.\n" +
                    "(töröld ki elöbb a háziállatot)", ButtonType.OK);
            alert.showAndWait();
            System.err.println(e);
        }
        personsLoad();
    }


    //háziállat törlése
    @FXML
    private void deletePet(ActionEvent event) {
        petService.delete(petSelect.getValue());
        petLoad();
    }


    //Név alapján lekérdezés(háziállat)
    @FXML
    private void selectPetByName(ActionEvent event) {
        pets = petService.getAllByNameContaining(petNameSelect.getText());
        petTable.getItems().setAll(pets);
    }


    //Gazda alapján lekérdezés
    @FXML
    private void selectByOwner(ActionEvent event) {
        pets = petService.getAllByOwner(petOwnerSelect.getValue());
        petTable.getItems().setAll(pets);
    }

    //Személy hozzáadása
    @FXML
    private void addPerson(ActionEvent event) {
        String name = personNameField.getText();
        Integer age = Integer.parseInt(personAgeField.getText());
        GenderEnum gender = personGenderField.getValue();
        PersonEntity entity = new PersonEntity(null, name, age, gender);
        personService.save(entity);
        personsLoad();
    }

    //személy tábla betöltése
    private void personsLoad() {
        persons = personService.getPersons();

        petOwnerField.setItems(FXCollections.observableArrayList(persons));
        petOwnerSelect.setItems(FXCollections.observableArrayList(persons));
        personSelect.setItems(FXCollections.observableArrayList(persons));

        personTable.getItems().setAll(persons);
    }

    //háziállat hozzáadása
    @FXML
    private void addPet(ActionEvent event) {
        String name = petNameField.getText();
        Integer age = Integer.parseInt(petAgeField.getText());
        SpeciesEnum species = petSpeciesField.getValue();
        PersonEntity owner = petOwnerField.getValue();
        PetEntity pet = new PetEntity(null, name, age, species, owner);
        petService.save(pet);
        petLoad();
    }

    //háziállat tábla betöltése
    private void petLoad() {
        pets = petService.getPets();
        petSelect.setItems(FXCollections.observableArrayList(pets));
        petTable.getItems().setAll(pets);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(Locale.GERMAN);         Javafx nem támogatja a magyar nyelvet
        tableInit();
    }

    private void tableInit() {
        petLoad();
        personsLoad();
    }

    //Táblák előzetes feltöltése
    @PostConstruct
    public void init() {
        //Györffy Bálint
        PersonEntity person = new PersonEntity(null, "Györffy Bálint", 21, GenderEnum.MAN);
        PersonEntity savedPerson = personService.save(person);

        PetEntity pet = new PetEntity(null, "Rexi", 4, SpeciesEnum.DOG, person);
        petService.save(pet);

        pet = new PetEntity(null, "Foltos", 2, SpeciesEnum.DOG, person);
        petService.save(pet);

        //Katona Attila
        person = new PersonEntity(null, "Katona Attila", 22, GenderEnum.MAN);
        personService.save(person);

        pet = new PetEntity(null, "Rexi", 4, SpeciesEnum.DOG, person);
        petService.save(pet);

        pet = new PetEntity(null, "Hali", 1, SpeciesEnum.FISH, person);
        petService.save(pet);

        //Kiss Pista
        person = new PersonEntity(null, "Kiss Pista", 30, GenderEnum.MAN);
        personService.save(person);
        //Nagy Luca
        person = new PersonEntity(null, "Nagy Luca", 25, GenderEnum.WOMAN);
        personService.save(person);

        //Példa Péter
        person = new PersonEntity(null, "Példa Péter", 20, GenderEnum.MAN);
        personService.save(person);
        //Kiss Anna
        person = new PersonEntity(null, "Kiss Anna", 35, GenderEnum.WOMAN);
        personService.save(person);

    }
}
