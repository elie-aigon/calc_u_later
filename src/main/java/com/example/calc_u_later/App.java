package com.example.calc_u_later;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainFXML = new FXMLLoader(App.class.getResource("MainScene.fxml"));
        FXMLLoader calculatorFXML = new FXMLLoader(App.class.getResource("CalculatorTab.fxml"));
        FXMLLoader converterFXML = new FXMLLoader(App.class.getResource("ConverterTab.fxml"));

        Scene mainScene = new Scene(mainFXML.load());
        mainScene.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Calc_U_later");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("calc_u_later.png")));
        stage.setScene(mainScene);
        stage.setMinHeight(450);
        stage.setMinWidth(575);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}