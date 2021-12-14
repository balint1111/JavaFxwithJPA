package com.example.spring_javafx;

import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.entities.PetEntity;
import com.example.spring_javafx.entities.SpeciesEnum;
import com.example.spring_javafx.service.PersonService;
import com.example.spring_javafx.service.PetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

    @FXML
    public VBox vBox;

    @FXML
    public Button btn1;

    @FXML
    public Button btn2;

    @FXML
    public Button addBtn;

    @FXML
    public TableView<PersonEntity> table;

    @FXML
    public TableColumn<PersonEntity, Long> id;

    @FXML
    public TableColumn<PersonEntity, String> name;

    @FXML
    public TableColumn<PersonEntity, Integer> age;

    @FXML
    public HBox nameHBox;

    @FXML
    public TextField nameField;

    @FXML
    public TextField ageField;

    @FXML
    public Button select1;


    @FXML
    private void setBtn1(ActionEvent event) {
        System.out.println("btn1");
        load();
    }

    @FXML
    private void setBtn2(ActionEvent event) {
        System.out.println("btn2");
        save();
    }

    @FXML
    private void setSelect1(ActionEvent event) {

    }

    @FXML
    private void addElement(ActionEvent event) {
        String name = nameField.getText();
        Integer age = Integer.parseInt(ageField.getText());
        PersonEntity entity = new PersonEntity();
        entity.setName(name);
        entity.setAge(age);
        personService.save(entity);
        load();
    }

    private void save() {
        System.out.println("items: "+table.getItems());
        System.out.println("persons: "+persons);
        personService.saveAll(persons);
    }

    private void load() {
        persons = personService.getPersons();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));

        table.getItems().setAll(persons);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn1.prefWidthProperty().bind(vBox.widthProperty());
        btn2.prefWidthProperty().bind(vBox.widthProperty());
        addBtn.prefWidthProperty().bind(vBox.widthProperty());

        tableInit();

    }

    private void tableInit(){
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        age.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        name.setOnEditCommit(event -> {
            PersonEntity person = event.getRowValue();
            person.setName(event.getNewValue());
        });
        age.setOnEditCommit(event -> {
            PersonEntity person = event.getRowValue();
            person.setAge(event.getNewValue());
        });

        load();
    }

    @PostConstruct
    public void init() {
        PersonEntity person = new PersonEntity(null,"Györffy Bálint", 21,null);
        PersonEntity savedPerson = personService.save(person);
        PersonEntity person2 = new PersonEntity(null,"Valaki", 30, savedPerson);
        PersonEntity savedPerson2 = personService.save(person2);

        PetEntity pet = new PetEntity(null,"rexi",4, SpeciesEnum.DOG,savedPerson);
        petService.save(pet);

        System.out.println(personService.getById(1L));
        System.out.println(petService.getById(1L));
    }
}
