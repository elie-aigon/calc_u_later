package com.example.calc_u_later.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.security.KeyManagementException;
import java.util.ResourceBundle;

public class ConverterController implements Initializable {
    @FXML
    private TextField input;
    @FXML
    private ComboBox<String> FromCbb;
    @FXML
    private ComboBox<String> ToCbb;
    @FXML
    private Button SubmitButton;
    ObservableList<String> FullUnits = FXCollections.observableArrayList("Centimeters", "Meters", "Inch", "Feet",
            "Grams", "Ounces", "Kilograms", "Pound",
            "Celsius", "Fahrenheit", "Kelvin",
            "Liters", "Gallon", "Cubic meters", "Cubic feet");
    ObservableList<String> Weight = FXCollections.observableArrayList("Grams", "Ounces", "Kilograms", "Pound");
    ObservableList<String> Temperature = FXCollections.observableArrayList("Celsius", "Fahrenheit", "Kelvin");
    ObservableList<String> Volume = FXCollections.observableArrayList("Liters", "Gallon", "Cubic meters", "Cubic feet");
    ObservableList<String> Length = FXCollections.observableArrayList("Centimeters", "Meters", "Inch", "Feet");
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FromCbb.setItems(FullUnits);
//        input.textProperty().addListener((observable, oldValue, newValue) -> {
//                    if (!newValue.matches("\\d*\\.?\\d*")) {
//                        input.setText(newValue.replaceAll("[^\\d.]", ""));
//                    }
//                });
    }
    @FXML
    private void UpdateToCbb() {
        ToCbb.getItems().clear();
        String value = FromCbb.getValue();
        if (Weight.contains(value)) {
            ToCbb.getItems().addAll(Weight);
        } else if (Temperature.contains(value)) {
            ToCbb.getItems().addAll(Temperature);
        } else if (Volume.contains(value)) {
            ToCbb.getItems().addAll(Volume);
        } else {
            ToCbb.getItems().addAll(Length);
        }
    }
    @FXML
    private void UpdateTextOutput(String output) {
        input.setText(output);
    }
    @FXML
    private void HandleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            DefineType();
        }
    }
//    Converter Logical Method
    @FXML
    private void DefineType() {
        String value = FromCbb.getValue();
        if (Weight.contains(value)) {
            ConvertWeight();
        } else if (Temperature.contains(value)) {
            ConvertTemperature();
        } else if (Volume.contains(value)) {
            ConvertVolume();
        } else {
            ConvertLength();
        }
    }

    private void ConvertWeight() {
        double output;
        switch (FromCbb.getValue()) {
            case "Ounces":
                output = Double.parseDouble(input.getText()) * 28.3495;
                break;
            case "Kilograms":
                output = Double.parseDouble(input.getText()) * 1000;
                break;
            case "Pound":
                output = Double.parseDouble(input.getText()) * 453.59237;
                break;
            default:
                output = Double.parseDouble(input.getText());
                break;
        }
        switch (ToCbb.getValue()) {
            case "Ounces":
                output *= 0.03527396;
                break;
            case "Kilograms":
                output *= 0.001;
                break;
            case "Pound":
                output *= 0.00220462;
                break;
            default:
                output = output;
                break;
        }
        String out = String.valueOf(output);
        out = out + " " + ToCbb.getValue();
        UpdateTextOutput(out);
    }
    private void ConvertVolume() {
        double output;
        switch (FromCbb.getValue()) {
            case "Cubic feet":
                output = Double.parseDouble(input.getText()) * 28.3168466;
                break;
            case "Gallon":
                output = Double.parseDouble(input.getText()) * 3.78541;
                break;
            case "Cubic meters":
                output = Double.parseDouble(input.getText()) * 1000;
                break;
            default:
                output = Double.parseDouble(input.getText());
                break;
        }
        switch (ToCbb.getValue()) {
            case "Cubic feet":
                output *= 0.0353147;
                break;
            case "Gallon":
                output *= 0.264172;
                break;
            case "Cubic meters":
                output /= 1000;
                break;
            default:
                output = output;
                break;
        }
        String out = String.valueOf(output);
        out = out + " " + ToCbb.getValue();
        UpdateTextOutput(out);
    }
    private void ConvertLength() {
        double output;
        switch (FromCbb.getValue()) {
            case "Meters":
                output = Double.parseDouble(input.getText()) * 100;
                break;
            case "Feet":
                output = Double.parseDouble(input.getText()) * 30.48;
                break;
            case "Inch":
                output = Double.parseDouble(input.getText()) * 2.54;
                break;
            default:
                output = Double.parseDouble(input.getText());
                break;
        }
        switch (ToCbb.getValue()) {
            case "Meters":
                output *= 0.01;
                break;
            case "Feet":
                output *= 0.0328084;
                break;
            case "Inch":
                output *= 0.393701;
                break;
            default:
                output = output;
                break;
        }
        String out = String.valueOf(output);
        out = out + " " + ToCbb.getValue();
        UpdateTextOutput(out);
    }
    private void ConvertTemperature() {
        double output;
        switch (FromCbb.getValue()) {
            case "Fahrenheit":
                output = (Double.parseDouble(input.getText()) - 32)* 1.8;
                break;
            case "Kelvin":
                output = Double.parseDouble(input.getText()) - 273.15;
                break;
            default:
                output = Double.parseDouble(input.getText());
                break;
        }
        switch (ToCbb.getValue()) {
            case "Fahrenheit":
                output = (output * 1.8) + 32;
                break;
            case "Kelvin":
                output += 273.15;
                break;
            default:
                output = output;
                break;
        }
        String out = String.valueOf(output);
        out = out + " " + ToCbb.getValue();
        UpdateTextOutput(out);
    }
}
