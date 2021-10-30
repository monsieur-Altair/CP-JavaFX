package com.gui.controller;

import com.SQLsupport.DBClass.User;
import com.implementation.client.OwnClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.gui.Constants.*;

public class UserAddMoneyController extends UserMenuController{

    @FXML
    private Label messageLabel;

    @FXML
    private Label TimeLabel;

    @FXML
    private Label moneyLabel;

    @FXML
    private TextField moneyField;

    @FXML
    private Button backButton;

    @FXML
    private Button addMoneyButton;

    @FXML
    private TextField cvField;

    @FXML
    private Label cvLabel;

    @FXML
    private TextField firstNumbers;

    @FXML
    private TextField firstTime;

    @FXML
    private TextField fourthNumbers;

    @FXML
    private Label numberCardLabel;

    @FXML
    private TextField secondNumbers;

    @FXML
    private TextField secondTime;

    @FXML
    private TextField thirdNumbers;

    @FXML
    public void initialize(){

        messageLabel.setText(" ");

        this.initMainScene();
    }

    @Override
    public void initMainScene(){
        super.initMainScene();
        backButton.setOnMouseClicked(event -> {switchScene(event,USER_PROFILE_FXML);});
        addMoneyButton.setOnMouseClicked(event -> {addMoney();});
        super.mainPane.setOnMouseClicked(event -> {messageLabel.setText("");});
    }

    private void addMoney() {

        User user=client.getUserProfile();
        int money=user.getMoney(), add_money=Integer.parseInt(moneyField.getText());

        if(checkAllFields()){
            client.sendDataToServer("add money");
            client.sendDataToServer(user.getId()+" "+money+" "+add_money);
            if(client.receiveResult()){
                user.setMoney(money+add_money);
                messageLabel.setText("Счет пополнен");
            }
        }
        else
            messageLabel.setText("Проверьте данные");


    }

    private boolean checkAllFields(){
        boolean res=true;
        int value;
        value = Integer.parseInt(firstNumbers.getText());
        if(!check(value,1000,9999)) return false;
        res &= check(value,1000,9999);

        value = Integer.parseInt(secondNumbers.getText());
        if(!check(value,1000,9999)) return false;
        res &=check(value,1000,9999);

        value = Integer.parseInt(thirdNumbers.getText());
        if(!check(value,1000,9999)) return false;

        value = Integer.parseInt(fourthNumbers.getText());
        if(!check(value,1000,9999)) return false;

        value = Integer.parseInt(firstTime.getText());
        if(!check(value,1,12)) return false;

        value = Integer.parseInt(secondTime.getText());
        if(!check(value,21,99)) return false;

        value = Integer.parseInt(cvField.getText());
        if(!check(value,100,999)) return false;

        value = Integer.parseInt(moneyField.getText());
        if(!check(value,1,1000000)) return false;

        return true;
    }

    private boolean check(double value,double min, double max){
        return (value<=max&&value>=min);
    }
}
