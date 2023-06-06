package com.example.calc_u_later.convert;

import com.example.calc_u_later.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Converter extends Controller {
    @FXML
    public ComboBox<String> ConvertFromCbb;
    @FXML
    public ComboBox<String> ConvertToCbb;
    ObservableList<String> FullUnits = FXCollections.observableArrayList("Centimètres", "Mètres", "Pouces", "Pieds",
            "Grammes", "Onces", "Kilogrammes", "Livres",
            "Celsius", "Fahrenheit", "Kelvin",
            "Litres", "Gallons", "Mètres cubes", "Pieds cubes");
    ObservableList<String> Weight = FXCollections.observableArrayList("Grammes", "Onces", "Kilogrammes", "Livres");
    ObservableList<String> Temperature = FXCollections.observableArrayList("Celsius", "Fahrenheit", "Kelvin");
    ObservableList<String> Volumes = FXCollections.observableArrayList("Litres", "Gallons", "Mètres cubes", "Pieds cubes");
    ObservableList<String> Length = FXCollections.observableArrayList("Centimètres", "Mètres", "Pouces", "Pieds");
    public void initialize() {
        ConvertFromCbb.setItems(FullUnits);
    }
    @FXML
    public void UpdateConvertCbb() {
        ConvertToCbb.getItems().clear();
        if (Weight.contains(ConvertFromCbb.getValue())) {
            ConvertToCbb.setItems(Weight);
        } else if (Temperature.contains(ConvertFromCbb.getValue())) {
            ConvertToCbb.setItems(Temperature);
        } else if (Volumes.contains(ConvertFromCbb.getValue())) {
            ConvertToCbb.setItems(Volumes);
        } else if (Length.contains(ConvertFromCbb.getValue())){
            ConvertToCbb.setItems(Length);
        } else {
            System.out.println("Blyat");
            ConvertToCbb.getItems().clear();
            ConvertFromCbb.getItems().clear();
        }
    }

}
