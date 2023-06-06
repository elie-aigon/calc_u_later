package com.example.calc_u_later;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    ObservableList<String> Units = FXCollections.observableArrayList("Centimètres", "Mètres", "Pouces", "Pieds");
    @FXML
    private ComboBox<String> fromCombobox;
    @FXML
    private ComboBox<String> toCombobox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromCombobox.setItems(Units);
        toCombobox.setItems(Units);
    }
}