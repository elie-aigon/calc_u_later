package com.example.calc_u_later;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TabPane tabPane = new TabPane();
        Node CalculatorContent = FXMLLoader.load(App.class.getResource("CalculatorTab.fxml"));
        Tab CalculatorTab = new Tab("Calculator");
        CalculatorTab.setContent(CalculatorContent);
        CalculatorTab.setClosable(false);
        tabPane.getTabs().add(CalculatorTab);

        Node ConverterContent = FXMLLoader.load(App.class.getResource("ConverterTab.fxml"));
        Tab ConverterTab = new Tab("Converter");
        ConverterTab.setContent(ConverterContent);
        ConverterTab.setClosable(false);
        tabPane.getTabs().add(ConverterTab);
        Scene scene = new Scene(tabPane);
        stage.setTitle("Calc_U_later");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}