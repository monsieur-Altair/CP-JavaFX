package com.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


import com.gui.Constants;
import com.gui.MainMenuGUI;
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
import com.SQLsupport.DBConnection;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.gui.Constants.*;

public class RegistrationController {

    private Parent root;
    private Stage stage;
    private Scene scene;

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




    class MyActionHandler implements EventHandler<ActionEvent>{

        DBConnection dbConnection;
        static int rowNumber=0;

        public MyActionHandler(){
            try{
                dbConnection=new DBConnection();
                dbConnection.init();

            }catch(IllegalAccessException | InstantiationException e){
                System.out.println(e);
            }
        }
        @Override
        public void handle(ActionEvent event) {

            String login=loginField.getText();
            String password=passwordFiled.getText();
            String address=addressField.getText();
            int money=Integer.parseInt(moneyField.getText());
            String lastName=lastNameFiled.getText();
            String firstName=firstNameField.getText();
            String phone=phoneField.getText();



            try(Connection conn=dbConnection.getMyConnection()){

                String sql0="SELECT * FROM db7.user";
                try(Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                    ResultSet result= stmt.executeQuery(sql0);
                    result.beforeFirst();
                    while(result.next())
                        rowNumber++;
                }
                String sql1="INSERT INTO "+DB_NAME+"."+USER_SCHEMA+" ("+
                        USER_ID+","+ USER_LOGIN+","+ USER_PASSWORD+","+
                        USER_FIRST_NAME+","+ USER_LAST_NAME+","+
                        USER_MONEY+","+USER_ADDRESS+","+USER_PHONE+")"+" VALUES (?,?,?,?,?,?,?,?)";
                try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                    prepStmt.setInt(1, ++rowNumber);
                    prepStmt.setString(2, login);
                    prepStmt.setString(3, password);
                    prepStmt.setString(4, firstName);
                    prepStmt.setString(5, lastName);
                    prepStmt.setInt(6, money);
                    prepStmt.setString(7, address);
                    prepStmt.setString(8, phone);
                    boolean res = prepStmt.executeUpdate() > 0;
                    System.out.println(res);

                }
            }catch (SQLException e){
                System.out.println(e);
            }

        }
    }
}
