package com.example.calc_u_later.controllers.toolsconverter;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HistoryObject extends VBox{
    public HistoryObject() {
    }
    public void AddHistoricElement(double input, double output, String from, String to) {
        Label element = new Label(input + " " + from + " <=> " + output + " " + to);

        getChildren().addAll(element);

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 0));
    }

}
