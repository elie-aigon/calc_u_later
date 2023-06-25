package com.example.calc_u_later.controllers.toolscalculator;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Stack;

public class HistoryObject extends VBox{

    public HistoryObject() { }

    public void AddHistoricElement(String calcul, String result) {
        Label element = new Label(calcul + result);

        getChildren().addAll(element);

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 0));
    }

}
