package com.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gui.MainMenuGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static com.gui.Constants.*;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button registrationButton;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {
        assert registrationButton != null : "fx:id=\"registrationButton\" was not injected: check your FXML file 'MainMenu.fxml'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'MainMenu.fxml'.";

        registrationButton.setOnAction((event)->{
            loadNewScene(REGISTRATION_FXML);
            registrationButton.getScene().getWindow().hide();
        });

        signInButton.setOnAction((event)->{
            loadNewScene(SIGN_IN_FXML);
            signInButton.getScene().getWindow().hide();
        });

    }

    public static void loadNewScene(String pathFXML){
        FXMLLoader loader = new FXMLLoader(MainMenuGUI.class.getResource(pathFXML));
        //loader.setLocation(MainMenuGUI.class.getResource(pathFXML));
        try{
            Parent root = loader.load();
            //Parent root=loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
