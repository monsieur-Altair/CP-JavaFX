package com.gui.controller.user;

import com.SQLsupport.DBClass.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Vector;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;

public class UserProductsController extends UserMenuController{

    private ObservableList<Product> dataFromServer, selectableProductList, comparisonList;
    private String successfulAdd,successfulCompr;

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
    private Button addToComparisonButton;

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

        dataFromServer = FXCollections.observableArrayList();
        selectableProductList = FXCollections.observableArrayList();
        comparisonList =FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_product"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("nameManufacturer"));

        this.initMainScene();

        this.selectAllProducts();
    }

    public void selectAllProducts(){
        super.client.sendData("select all products");
        super.client.sendData(" ");
        this.updateTable();
    }

    public void selectOneProduct(){
        String selectableName= searchField.getText();
        if(selectableName.equals(""))
            return;
        super.client.sendData("select one product");
        super.client.sendData(selectableName);

    }

    public void selectProductByManufacturer(){
        String selectableManufacturerName= filterField.getText();
        if(selectableManufacturerName.equals(""))
            return;
        super.client.sendData("select by manufacturer");
        super.client.sendData(selectableManufacturerName);
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
    public void initMainScene(){

        super.initMainScene();

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
            if(selectableProductList.size()!=0){
                super.client.setSelectableProductForReview(selectableProductList.get(0));
                super.switchScene(event,PRODUCTS_REVIEW_FXML);
            }
        });

        addToBasketButton.setOnMouseClicked(event -> {
            selectOneProduct();
            Vector<Product> products = super.client.receiveProducts();
            if(products!=null){
                super.client.sendData("add to basket");
                super.client.sendData(super.client.getUserProfile().getId()+" "+
                        products.elementAt(0).getId_product());

                if(super.client.receiveResult()){
                    messageLabel.setText(successfulAdd);
                }
            }
        });

        addToComparisonButton.setOnMouseClicked(event -> {
            if(selectableProductList.size()==0)
                return;
            if(comparisonList.size()<2){
                comparisonList.add(selectableProductList.get(0));
                messageLabel.setText(successfulCompr);
            }
            if(comparisonList.size()==2) {
                dataFromServer.clear();
                dataFromServer.addAll(comparisonList);
                productsTable.setItems(dataFromServer);
                comparisonList.clear();

            }
        });

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    @Override
    protected void switchLanguage(int language_count){
        super.switchLanguage(language_count);
        headLabel.setText(LABEL_PRODUCTS_TEXT[language_count]);
        idColumn.setText(PRODUCTS_NUMBER_TEXT[language_count]);
        nameColumn.setText(PRODUCTS_NAME_TEXT[language_count]);
        typeColumn.setText(PRODUCTS_TYPE_TEXT[language_count]);
        costColumn.setText(PRODUCTS_COST_TEXT[language_count]);
        countColumn.setText(PRODUCTS_COUNT_TEXT[language_count]);
        manufacturerColumn.setText(PRODUCTS_MANUF_TEXT[language_count]);
        reviewButton.setText(PRODUCTS_REVIEW_TEXT[language_count]);
        addToBasketButton.setText(PRODUCTS_BASKET_TEXT[language_count]);
        searchButton.setText(PRODUCTS_SEARCH_TEXT[language_count]);
        addToComparisonButton.setText(PRODUCTS_COMPARISON_TEXT[language_count]);
        filterButton.setText(PRODUCTS_FILTER_TEXT[language_count]);
        searchField.setPromptText(PRODUCTS_CHOOSE_PR_TEXT[language_count]);
        filterField.setPromptText(PRODUCTS_CHOOSE_MANUF_TEXT[language_count]);
        successfulAdd=PRODUCTS_ADD_SUCCESSFUL_TEXT[language_count];
        successfulCompr=PRODUCTS_COMPARISON_SUCCESSFUL_TEXT[language_count];
    }

}
