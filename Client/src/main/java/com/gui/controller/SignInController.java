package com.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import com.SQLsupport.DBClass.User;
import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.event.ActionEvent;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.gui.Constants.MAIN_MENU_FXML;
import static com.gui.Constants.USER_MENU_FXML;

public class SignInController {

    private Parent root;
    private Stage stage;
    private Scene scene;
    private OwnClient client;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView arrowButton;

    @FXML
    private URL location;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordFTextiled;

    @FXML
    private Button signInButton;

    @FXML
    private Label signInLabel;

    @FXML
    void initialize() {

        signInButton.setOnAction(new MyActionHandler());
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

    class MyActionHandler implements EventHandler<ActionEvent> {

        public MyActionHandler(){};

        @Override
        public void handle(ActionEvent event) {

            /*String login=loginTextField.getText();
            String password=passwordFTextiled.getText();*/

            String login="sekiro";
            String password="132ds34";

            client= OwnClient.getInstance();
            client.sendDataToServer("signIn");
            String dataFromClient=login+" "+password;
            client.sendDataToServer(dataFromClient);

            Vector<User> users = client.receiveUsers();
            if(users!=null){
                client.setUserProfile(users.elementAt(0));
                try {
                    switchToUserMenuScene(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("cannot receive user");
            }
        }
    }

}
