package com.gui.controller;

import com.SQLsupport.DBClass.Product;
import com.gui.MainMenuGUI;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Vector;

import static com.gui.Constants.*;

public class UserProductsController extends UserMenuController{

    private ObservableList<Product> dataFromServer, selectableProductList;

    @FXML
    private TableColumn<Product, Integer> costColumn;

    @FXML
    private Label messageLabel;

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
    private Button reviewButton;

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
        super.switchTheme(path);

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
    }

    public void selectAllProducts(){
        super.client.sendDataToServer("select all products");
        super.client.sendDataToServer(" ");
        this.updateTable();
    }

    public void selectOneProduct(){
        String selectableName= searchField.getText();
        if(selectableName.equals(""))
            return;
        super.client.sendDataToServer("select one product");
        super.client.sendDataToServer(selectableName);

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

        messageLabel.setText(" ");

        productsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableProductList.clear();
                        selectableProductList.add(productsTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectableProductList.get(0).getName();
                        String selectableManuf = selectableProductList.get(0).getNameManufacturer();
                        filterField.setText(selectableManuf);
                        searchField.setText(selectableName);
                        messageLabel.setText(" ");
                    }
                }
        );

        searchButton.setOnMouseClicked(event -> {
            selectOneProduct();
            this.updateTable();
        });

        filterButton.setOnMouseClicked(event -> {selectProductByManufacturer();});
        reviewButton.setOnMouseClicked(event -> {
            super.client.setSelectableProductForReview(selectableProductList.get(0));
            super.switchScene(event,PRODUCTS_REVIEW_FXML);});

        addToBasketButton.setOnMouseClicked(event -> {
            selectOneProduct();
            Vector<Product> products = super.client.receiveProducts();
            if(products!=null){
                super.client.sendDataToServer("add to basket");
                super.client.sendDataToServer(super.client.getUserProfile().getId()+" "+
                        products.elementAt(0).getId_product());

                if(super.client.receiveResult()){
                    messageLabel.setText("Товар добавлен");
                }
            }

        });
    }

}
