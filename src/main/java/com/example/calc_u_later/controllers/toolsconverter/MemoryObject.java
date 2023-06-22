package com.example.calc_u_later.controllers.toolsconverter;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MemoryObject extends VBox {
    public MemoryObject() {
        AddMemoryElement("exemple1");
    }
    private void AddMemoryElement(String input) {
        Label element = new Label(input);

        Button Mplus = new Button("M+");
        Button Mmoins = new Button("M-");
        Button MS = new Button("MS");

        HBox a = new HBox();
        a.getChildren().addAll(Mplus, Mmoins, MS);
        getChildren().addAll(element, a);

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 0));
    }
}
