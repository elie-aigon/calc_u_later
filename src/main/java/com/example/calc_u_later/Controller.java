package com.example.calc_u_later;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ComboBox<String> ConvertFromCbb;
    @FXML
    public ComboBox<String> ConvertToCbb;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}