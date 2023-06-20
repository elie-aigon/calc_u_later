package com.example.calc_u_later.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//import com.example.calc_u_later.models.calculator.CalculatorModel;

public class CalculatorController implements Initializable {
    @FXML private Label inputExpr;
    @FXML private Label inputValue;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("a");
    }

    EventHandler<ActionEvent> buttonEvent() {
        return event -> {
            Button valueButton = (Button) event.getTarget();
            String buttonLabel = valueButton.getText();

            switch (buttonLabel) {
                case "=":
            }
        };
    }
}
