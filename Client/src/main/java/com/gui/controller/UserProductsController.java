package com.gui.controller;

import com.SQLsupport.DBClass.Manufacturer;
import com.SQLsupport.DBClass.Product;
import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Vector;

import static com.gui.Constants.*;

public class UserProductsController extends UserMenuController{

    private ObservableList<Product> list;

    @FXML
    private TableColumn<Product, Integer> costColumn;

    @FXML
    private TableColumn<Product, Integer> countColumn;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> manufacturerColumn;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    void initialize(){
        super.client = OwnClient.getInstance();

        String path=!super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);

        list= FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("id_manufacturer"));

        super.client.sendDataToServer("select all products");
        super.client.sendDataToServer(" ");
        Vector<Product> products = super.client.receiveProducts();
        list.clear();
        list.addAll(products);
        productsTable.setItems(list);

        this.initMainButtons();
    }

    @Override
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
        super.headLabel.getStyleClass().add("label-header");

        productsTable.getStyleClass().add("table-view");
        idColumn.getStyleClass().add("column-header-background");
        typeColumn.getStyleClass().add("column-header-background");
        costColumn.getStyleClass().add("column-header-background");
        countColumn.getStyleClass().add("column-header-background");
        nameColumn.getStyleClass().add("column-header-background");
        manufacturerColumn.getStyleClass().add("column-header-background");
    }
}
