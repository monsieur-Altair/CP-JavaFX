package com.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import com.SQLsupport.DBClass.Manufacturer;
import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import static com.gui.Constants.DARK_THEME_PATH;
import static com.gui.Constants.LIGHT_THEME_PATH;

public class UserManufacturerController extends UserMenuController{

    private ObservableList<Manufacturer> list;

    @FXML
    private TableColumn<Manufacturer, String> countryColumn;

    @FXML
    private TableColumn<Manufacturer, String> emailColumn;

    @FXML
    private TableColumn<Manufacturer, Integer> idColumn;

    @FXML
    private TableView<Manufacturer> manufacturerTable;

    @FXML
    private TableColumn<Manufacturer, String> nameColumn;

    @FXML
    private TableColumn<Manufacturer, Double> ratingColumn;

    @FXML
    void initialize() {
        super.client = OwnClient.getInstance();

        String path=!super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);

        list= FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Integer>("id_manufacturer"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("country"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("email"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Double>("rating"));

        super.client.sendDataToServer("select all manufacturer");
        super.client.sendDataToServer(" ");
        Vector<Manufacturer> manufacturers = super.client.receiveManufacturers();
        list.clear();
        list.addAll(manufacturers);
        manufacturerTable.setItems(list);

        this.initMainButtons();
    }

    public void initMainButtons(){

        super.initMainButtons();

        super.themeButton.setOnMouseClicked((event)->{
            String path1=super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
            switchTheme(path1);
            super.client.switchTheme();
        });
    }

    public void switchTheme(String path){

        ObservableList<String> styleSheets=super.headerPane.getStylesheets();

        String css = MainMenuGUI.class.getResource(path).toExternalForm();
        styleSheets.add(css);

        if(styleSheets.size()>1)
            styleSheets.remove(0);

        super.headerPane.getStyleClass().add("header");
        super.mainPane.getStyleClass().add("main");
        super.leftPane.getStyleClass().add("left");

    }

}
