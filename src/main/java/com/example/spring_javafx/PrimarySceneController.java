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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
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
    public HBox hBox;

    @FXML
    public VBox vBox1;

    @FXML
    public VBox vBox2;

    @FXML
    public Button btn1;

    @FXML
    public Button btn2;

    @FXML
    public Button addBtn;

    @FXML
    public TableView<PersonEntity> table;

    @FXML
    public TableView<PetEntity> petTable;

    @FXML
    public TableColumn<PersonEntity, Long> petId;

    @FXML
    public TableColumn<PersonEntity, String> petNameColumn;

    @FXML
    public TableColumn<PersonEntity, Integer> petAgeColumn;

    @FXML
    public TableColumn<PersonEntity, String> petSpeciesColumn;

    @FXML
    public TableColumn<PersonEntity, String> petOwnerIdColumn;

    @FXML
    public TableColumn<PersonEntity, Long> personIdColumn;

    @FXML
    public TableColumn<PersonEntity, String> personNameColumn;

    @FXML
    public TableColumn<PersonEntity, Integer> personAgeColumn;

    @FXML
    public TableColumn<PersonEntity, String> personGenderColumn;


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
    public Button select1;


    @FXML
    private void setBtn1(ActionEvent event) {
        System.out.println("btn1");
        personsLoad();
    }

    @FXML
    private void setBtn2(ActionEvent event) {
        System.out.println("btn2");
        personsSave();
    }

    @FXML
    private void setSelect1(ActionEvent event) {

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

    private void personsSave() {
        personService.saveAll(persons);
    }

    private void personsLoad() {
        persons = personService.getPersons();

//        personIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        personNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        personAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
//        personGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        petOwnerField.setItems(FXCollections.observableArrayList(persons));

        table.getItems().setAll(persons);
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

    private void petSave() {
        petService.saveAll(pets);
    }

    private void petLoad() {
        pets = petService.getPets();
        petTable.getItems().setAll(pets);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBox1.prefWidthProperty().bind(hBox.widthProperty().divide(2));
        vBox2.prefWidthProperty().bind(hBox.widthProperty().divide(2));
        btn1.prefWidthProperty().bind(vBox1.widthProperty());
        btn2.prefWidthProperty().bind(vBox1.widthProperty());

        personGenderField.setItems(FXCollections.observableArrayList(GenderEnum.values()));
        petSpeciesField.setItems(FXCollections.observableArrayList(SpeciesEnum.values()));


        tableInit();

    }

    private void tableInit(){
        personNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        personAgeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        personNameColumn.setOnEditCommit(event -> {
            PersonEntity person = event.getRowValue();
            person.setName(event.getNewValue());
        });
        personAgeColumn.setOnEditCommit(event -> {
            PersonEntity person = event.getRowValue();
            person.setAge(event.getNewValue());
        });

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
