package com.gui.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationGUI extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        //var font= Font.loadFont(getClass().getResourceAsStream("resources/fonts/Ephesis-Regular.ttf"), 14);

        FXMLLoader fxmlLoader = new FXMLLoader(AuthorizationGUI.class.getResource("Authorization.fxml"));
        //System.out.println(font);

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { Application.launch(); }
}