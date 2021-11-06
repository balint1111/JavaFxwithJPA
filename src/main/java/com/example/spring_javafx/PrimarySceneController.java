package com.example.spring_javafx;

import com.example.spring_javafx.entities.PersonEntity;
import com.example.spring_javafx.service.PersonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PrimarySceneController implements Initializable {

    @Autowired
    private PersonService personService;

    List<PersonEntity> persons;

    @FXML
    public VBox vBox;

    @FXML
    public Button btn1;

    @FXML
    public Button btn2;

    @FXML
    public TableView<PersonEntity> table;

    @FXML
    public TableColumn<PersonEntity, Long> id;

    @FXML
    public TableColumn<PersonEntity, String> name;

    @FXML
    public TableColumn<PersonEntity, Integer> age;


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
        tableInit();

    }

    private void tableInit(){
        System.out.println("inteliJ");
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
}
