package com.gui.controller.admin;

import com.SQLsupport.DBClass.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Vector;

import static com.gui.Constants.*;
import static com.gui.LanguageSupport.*;
import static com.gui.LanguageSupport.PRODUCTS_COMPARISON_SUCCESSFUL_TEXT;

public class AdminProductsController extends AdminMenuController{
    private ObservableList<Product> dataFromServer, selectableProductList;
    private String successfulAdd,successfulCompr;

    @FXML
    private Button addCountButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField costField;

    @FXML
    private TextField countField;

    @FXML
    private Button createProductButton;

    @FXML
    private Button deleteCountButton;

    @FXML
    private Label headLabel;

    @FXML
    private Button languageButton;

    @FXML
    private TextField manufField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField typeField;

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
    private Button searchButton;

    @FXML
    void initialize(){

        dataFromServer = FXCollections.observableArrayList();
        selectableProductList = FXCollections.observableArrayList();
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

    public void updateTable(){
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receiveProducts());
        productsTable.setItems(dataFromServer);
        searchField.setText("");

        costField.setText("");
        nameField.setText("");
        numberField.setText("");
        manufField.setText("");
        typeField.setText("");

        countField.setText("");
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
                        searchField.setText(selectableName);
                        messageLabel.setText(" ");
                    }
                }
        );

        searchButton.setOnMouseClicked(event -> {
            selectOneProduct();
            this.updateTable();
        });

        createProductButton.setOnMouseClicked(event -> {createProduct();});
        deleteButton.setOnMouseClicked(event -> {deleteOneProduct();});
        deleteCountButton.setOnMouseClicked(event -> {updateCount(-1);});
        addCountButton.setOnMouseClicked(event -> {updateCount(1);});

        languageButton.setOnMouseClicked(event -> {
            int language_count1=client.isRussianLanguage()?LANGUAGE_ENGLISH:LANGUAGE_RUSSIAN;
            this.switchLanguage(language_count1);
            client.switchLanguage();
        });
    }

    private void updateCount(int value) {
        Product product=selectableProductList.get(0);
        if (product==null)
            return;
        int newCount=value * Integer.parseInt(countField.getText());
        int id=product.getId_product();
        client.sendData("edit product count");
        client.sendData(id+" "+newCount);

        if(client.receiveResult()){
            this.selectAllProducts();
        }
    }

    private void deleteOneProduct() {
        Product product=selectableProductList.get(0);
        if (product==null)
            return;
        client.sendData("delete one product");
        int id=product.getId_product();
        client.sendData(Integer.toString(id));

        if(client.receiveResult()){
            this.selectAllProducts();
        }
    }

    private void createProduct() {
        String cost, name, number, manuf, type;

        cost = costField.getText();
        name = nameField.getText();
        number = numberField.getText();
        manuf = manufField.getText();
        type = typeField.getText();

        client.sendData("create product");
        client.sendData(name+"@@@"+type+"@@@"+cost+"@@@"+number+"@@@"+manuf);

        if(client.receiveResult()){
            this.selectAllProducts();
            //messageLabel.setText("Создан");
        }
        else
            System.out.println("oops");
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
        searchButton.setText(PRODUCTS_SEARCH_TEXT[language_count]);
        searchField.setPromptText(PRODUCTS_CHOOSE_PR_TEXT[language_count]);
        successfulAdd=PRODUCTS_ADD_SUCCESSFUL_TEXT[language_count];
        successfulCompr=PRODUCTS_COMPARISON_SUCCESSFUL_TEXT[language_count];
    }

}
