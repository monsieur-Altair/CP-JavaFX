package com.gui.controller;

import com.SQLsupport.DBClass.Product;
import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;

public class UserProductsController extends UserMenuController{

    private ObservableList<Product> dataFromServer, selectableProductList;

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
    private TextField searchField;

    @FXML
    private Button addToBasketButton;

    @FXML
    private TextField filterField;

    @FXML
    private Button filterButton;

    @FXML
    private Button searchButton;

    @FXML
    void initialize(){
        super.client = OwnClient.getInstance();

        String path=!super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);

        dataFromServer = FXCollections.observableArrayList();
        selectableProductList = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("nameManufacturer"));

        this.selectAllProducts();
        this.initMainButtons();

        searchButton.setOnMouseClicked(event -> {selectOneProduct();});
        filterButton.setOnMouseClicked(event -> { selectProductByManufacturer();});
    }

    public void selectAllProducts(){
        super.client.sendDataToServer("select all products");
        super.client.sendDataToServer(" ");
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receiveProducts());
        productsTable.setItems(dataFromServer);
    }

    public void selectOneProduct(){
        String selectableName= searchField.getText();
        if(selectableName.equals(""))
            return;
        super.client.sendDataToServer("select one product");
        super.client.sendDataToServer(selectableName);
        this.updateTable();
    }

    public void selectProductByManufacturer(){
        String selectableManufacturerName= filterField.getText();
        if(selectableManufacturerName.equals(""))
            return;
        super.client.sendDataToServer("select by manufacturer");
        super.client.sendDataToServer(selectableManufacturerName);
        this.updateTable();
    }

    public void updateTable(){
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receiveProducts());
        productsTable.setItems(dataFromServer);
        filterField.setText("");
        searchField.setText("");
    }

    @Override
    public void initMainButtons(){

        super.initMainButtons();

        super.themeButton.setOnMouseClicked((event)->{
            String path1=super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
            switchTheme(path1);
            super.client.switchTheme();
        });

        productsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableProductList.clear();
                        selectableProductList.add(productsTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectableProductList.get(0).getName();
                        String selectableManuf = selectableProductList.get(0).getNameManufacturer();
                        filterField.setText(selectableManuf);
                        searchField.setText(selectableName);
                    }
                }
        );
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
