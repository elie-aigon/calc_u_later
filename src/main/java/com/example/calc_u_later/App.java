package com.example.calc_u_later;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calc_U_later");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("calc_u_later.png")));
        stage.setScene(scene);
        stage.setMinHeight(450);
        stage.setMinWidth(575);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}