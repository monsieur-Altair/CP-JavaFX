package com.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.gui.Constants.*;

public class UserMenuController {

    protected OwnClient client;
    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    @FXML
    protected Button basketButton;

    @FXML
    private AnchorPane headerPane;

    @FXML
    private Button languageButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button closeButton;

    @FXML
    private VBox leftPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button manufacturerButton;

    @FXML
    private Button productButton;

    @FXML
    private Label headLabel;

    @FXML
    private Button themeButton;

    public UserMenuController() {
    }

    @FXML
    void initialize() {
        client = OwnClient.getInstance();

        String path=!client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);

        headLabel.setText("Добро пожаловать, "+client.getUserProfile().getLogin()+"!");

        themeButton.setOnMouseClicked((event)->{
            String path1=client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
            switchTheme(path1);
            client.switchTheme();
        });

        manufacturerButton.setOnMouseClicked(event->{
            try {
                root = FXMLLoader.load(MainMenuGUI.class.getResource(USER_MANUFACTURER_FXML));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        closeButton.setOnMouseClicked(event->{
            client.sendDataToServer("exit");
            stage = (Stage)closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void switchTheme(String themePath){
        ObservableList<String> styleSheets=headerPane.getStylesheets();

        String css = MainMenuGUI.class.getResource(themePath).toExternalForm();
        styleSheets.add(css);

        if(styleSheets.size()>1)
            styleSheets.remove(0);

        headerPane.getStyleClass().add("header");
        mainPane.getStyleClass().add("main");
        leftPane.getStyleClass().add("left");
        headLabel.getStyleClass().add("label-header");
    }



}

