package com.gui.controller;

import com.SQLsupport.DBClass.Purchase;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.DARK_THEME_PATH;
import static com.gui.Constants.LIGHT_THEME_PATH;

public class UserBasketContoller extends UserMenuController{

    private ObservableList<Purchase> dataFromServer, selectablePurchasesList;

    @FXML
    private Label allCostLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button buyAllButton;

    @FXML
    private Button buyOneButton;

    @FXML
    private Label currentAccountLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button printButton;

    @FXML
    private Label oneCostLabel;

/*    @FXML
    private Button searchButton;*/

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Purchase, Integer> costColumn;

    @FXML
    private TableColumn<Purchase, Integer> idColumn;

    @FXML
    private TableColumn<Purchase, String> manufacturerColumn;

    @FXML
    private TableView<Purchase> purchaseTable;

    @FXML
    private TableColumn<Purchase, String> nameColumn;

    @FXML
    private TableColumn<Purchase, String> typeColumn;

    @FXML
    void initialize(){
        super.client = OwnClient.getInstance();

        String path=!super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        super.switchTheme(path);

        dataFromServer = FXCollections.observableArrayList();
        selectablePurchasesList = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_purchase"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("product_type"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("product_cost"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer_name"));

        this.selectAllProductsFromBasket();

        currentAccountLabel.setText("Ваш текущий счет:\t\t\t\t\t"+client.getUserProfile().getMoney());
        allCostLabel.setText("Всего в корзине товаров на сумму:\t"+calculateSumInBasket());
        oneCostLabel.setText("Выбран товар на сумму:\t\t\t\t0");
        messageLabel.setText(" ");

        this.initMainButtons();
    }



    public void selectAllProductsFromBasket(){
        super.client.sendDataToServer("select all purchases");
        super.client.sendDataToServer(Integer.toString(super.client.getUserProfile().getId()));
        this.updateTable();
    }

    public void initMainButtons(){
        super.initMainButtons();

        deleteButton.setOnMouseClicked(event -> {deleteOnePurchase();});
        buyOneButton.setOnMouseClicked(event -> {buyOneProduct();});
        buyAllButton.setOnMouseClicked(event -> {buyAllProducts();});
        printButton.setOnMouseClicked( event -> {printBasket();});

        purchaseTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectablePurchasesList.clear();
                        selectablePurchasesList.add(purchaseTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectablePurchasesList.get(0).getProduct_name();
                        searchField.setText(selectableName);
                        oneCostLabel.setText("Выбран товар на сумму:\t\t\t\t"+ selectablePurchasesList.get(0).getProduct_cost());
                        messageLabel.setText(" ");
                    }
                }
        );
    }

    private void printBasket() {
        super.client.sendDataToServer("print basket");
        super.client.sendDataToServer(Integer.toString(super.client.getUserProfile().getId()));
        String filePath = super.client.receiveFilePath();
        messageLabel.setText("Файл создан: "+filePath);
    }

    private void buyAllProducts(){
        int money=client.getUserProfile().getMoney();
        int product_cost=calculateSumInBasket();
        super.client.sendDataToServer("buy all products");
        super.client.sendDataToServer(super.client.getUserProfile().getId()+" "
                +money+" "
                +product_cost);

        if(super.client.receiveResult()){
            deleteAllPurchase();
            client.getUserProfile().setMoney(money-product_cost);
            ///////////////////////////////////
            currentAccountLabel.setText("Ваш текущий счет:\t\t\t\t\t"+client.getUserProfile().getMoney());
            messageLabel.setText("Товары куплены");
        }
        else
            messageLabel.setText("Недостаточно денег");
    }

    private void deleteAllPurchase() {
        super.client.sendDataToServer("delete all purchases");
        super.client.sendDataToServer(Integer.toString(super.client.getUserProfile().getId()));

        if(super.client.receiveResult()){
            selectAllProductsFromBasket();
            allCostLabel.setText("Всего в корзине товаров на сумму:\t"+calculateSumInBasket());
        }
    }

    private void buyOneProduct() {
        if(selectablePurchasesList.size()==0)
            return;
        int money=client.getUserProfile().getMoney();
        int product_cost=selectablePurchasesList.get(0).getProduct_cost();
        super.client.sendDataToServer("buy one product");
        super.client.sendDataToServer(super.client.getUserProfile().getId()+" "
                +money+" "
                +product_cost);

        if(super.client.receiveResult()){
            deleteOnePurchase();
            client.getUserProfile().setMoney(money-product_cost);
            ///////////////////////////////////
            currentAccountLabel.setText("Ваш текущий счет:\t\t\t\t\t"+client.getUserProfile().getMoney());
            messageLabel.setText("Товар куплен");
        }
        else
            messageLabel.setText("Недостаточно денег");
    }

    private void deleteOnePurchase() {
        if(selectablePurchasesList.size()==0)
            return;
        super.client.sendDataToServer("delete one purchase");
        super.client.sendDataToServer(Integer.toString(selectablePurchasesList.get(0).getId_purchase()));

        if(super.client.receiveResult()){
            selectAllProductsFromBasket();
            allCostLabel.setText("Всего в корзине товаров на сумму:\t"+calculateSumInBasket());
            messageLabel.setText("Товар удален");
        }
    }

    private int calculateSumInBasket() {
        int sum=0;
        if(dataFromServer.size()>0)
            for(Purchase purchase: dataFromServer)
                sum+=purchase.getProduct_cost();
        return sum;
    }

    public void updateTable(){
        dataFromServer.clear();
        dataFromServer.addAll(super.client.receivePurchases());
        purchaseTable.setItems(dataFromServer);
        searchField.setText("");
        oneCostLabel.setText("Выбран товар на сумму:\t\t\t\t0");
    }
}
