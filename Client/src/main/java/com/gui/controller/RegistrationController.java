package com.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


import com.SQLsupport.DBClass.User;
import com.gui.Constants;
import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.gui.Constants.*;

public class RegistrationController {

    private Parent root;
    private Stage stage;
    private Scene scene;
    private OwnClient client;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameFiled;

    @FXML
    private ImageView backArrow;

    @FXML
    private TextField loginField;

    @FXML
    private TextField moneyField;

    @FXML
    private PasswordField passwordFiled;

    @FXML
    private TextField phoneField;

    @FXML
    private Button registrationButton;

    @FXML
    private Label registrationLabel;

    @FXML
    void initialize() {
        registrationButton.setOnAction(new MyActionHandler());

    }

    public void switchToMainMenuScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(MainMenuGUI.class.getResource(MAIN_MENU_FXML));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserMenuScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(MainMenuGUI.class.getResource(USER_MENU_FXML));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    class MyActionHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {

            String login=loginField.getText();
            String password=passwordFiled.getText();
            int role=0;
            String address=addressField.getText();
            int money=Integer.parseInt(moneyField.getText());
            String lastName=lastNameFiled.getText();
            String firstName=firstNameField.getText();
            String phone=phoneField.getText();


            client=OwnClient.getInstance();
            client.sendDataToServer("registration");
            String dataFromClient=login+" "+password+" "+role+" "+firstName+" "+
                    lastName+" "+money+" "+address+" "+phone;
            client.sendDataToServer(dataFromClient);

            boolean result = client.receiveResult();
            if(result){
                try {
                    client.setUserProfile(new User(login,password,role,firstName,lastName,money,address,phone));
                    switchToUserMenuScene(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }
    }
}
