package com.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gui.MainMenuGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static com.gui.Constants.*;

public class MainMenuController {

    private Stage stage;
    private Parent root;
    private Scene scene;


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
       /*registrationButton.setOnAction((event)->{
            loadNewScene(REGISTRATION_FXML);
            registrationButton.getScene().getWindow().hide();
        });

        signInButton.setOnAction((event)->{
            loadNewScene(SIGN_IN_FXML);
            signInButton.getScene().getWindow().hide();
        });*/

    }

    public void switchToRegisterScene(ActionEvent event) throws IOException{
        root = FXMLLoader.load(MainMenuGUI.class.getResource(REGISTRATION_FXML));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignInScene(ActionEvent event) throws IOException{
        root = FXMLLoader.load(MainMenuGUI.class.getResource(SIGN_IN_FXML));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


/*    public static void loadNewScene(String pathFXML){
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
    }*/
}
