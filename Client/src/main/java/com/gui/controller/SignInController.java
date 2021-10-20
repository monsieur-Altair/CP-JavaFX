package com.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML
    private ResourceBundle resources;

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
        assert loginTextField != null : "fx:id=\"loginTextField\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert passwordFTextiled != null : "fx:id=\"passwordFTextiled\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert signInButton != null : "fx:id=\"signInButton\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert signInLabel != null : "fx:id=\"signInLabel\" was not injected: check your FXML file 'SignIn.fxml'.";

    }

}
