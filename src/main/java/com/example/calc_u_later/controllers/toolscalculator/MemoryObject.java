package com.example.calc_u_later.controllers.toolscalculator;


import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;



public class MemoryObject extends VBox {
    private static List<String> memoryStack = new LinkedList<>();

    public MemoryObject() { }

    public void AddMemoryElement(Label element, Button Add, Button Sub) {
        HBox a = new HBox();
        memoryStack.add(element.getText());

        Button MClear = new Button("MC");

        a.getChildren().addAll(Add, Sub, MClear);
        getChildren().addAll(element, a);

        MClear.setOnAction(e -> {
            getChildren().remove(element);
            getChildren().remove(a);
        });

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 0));
    }



    public void CalculatorButtonMAdd(String operand) {
        String newValue = Double.toString(Double.parseDouble(memoryStack.get(0)) + Double.parseDouble(operand));

        memoryStack.set(0, newValue);
    }

    public void CalculatorButtonMSub(String operand) {
        String newValue = Double.toString(Double.parseDouble(memoryStack.get(0)) - Double.parseDouble(operand));

        memoryStack.set(0, newValue);
    }

    public void ClearMemory() {
        getChildren().clear();
    }

    public String CalculatorButtonMR() {
        return memoryStack.get(0);
    }
}