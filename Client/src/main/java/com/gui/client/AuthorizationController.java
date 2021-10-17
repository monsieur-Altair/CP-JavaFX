package com.gui.client;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.SQLsupport.DBConnection;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordFTextiled;

    @FXML
    private Button registrationButton;

    @FXML
    void initialize() {
        assert emailTextField != null : "fx:id=\"emailTextField\" was not injected: check your FXML file 'Authorization.fxml'.";
        assert loginTextField != null : "fx:id=\"loginTextField\" was not injected: check your FXML file 'Authorization.fxml'.";
        assert passwordFTextiled != null : "fx:id=\"passwordFTextiled\" was not injected: check your FXML file 'Authorization.fxml'.";
        assert registrationButton != null : "fx:id=\"registrationButton\" was not injected: check your FXML file 'Authorization.fxml'.";

        registrationButton.setOnAction(new MyActionHandler());

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

            String email=emailTextField.getText();
            String login=loginTextField.getText();
            String password=passwordFTextiled.getText();



            try(Connection conn=dbConnection.getMyConnection()){

                String sql0="SELECT * FROM db7.user";
                try(Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                    ResultSet result= stmt.executeQuery(sql0);
                    result.beforeFirst();
                    while(result.next())
                        rowNumber++;
                }
                String sql1="INSERT INTO db7.user(id,name,password,email) VALUES (?,?,?,?)";
                try(PreparedStatement prepStmt=conn.prepareStatement(sql1)){
                    prepStmt.setInt(1, ++rowNumber);
                    prepStmt.setString(2, login);
                    prepStmt.setString(3, password);
                    prepStmt.setString(4, email);
                    boolean res = prepStmt.executeUpdate() > 0;
                    System.out.println(res);

                }
            }catch (SQLException e){
                System.out.println(e);
            }

            emailTextField.setText("");
            loginTextField.setText("");
            passwordFTextiled.setText("");
        }
    }
}
