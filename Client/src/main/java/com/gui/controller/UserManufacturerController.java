package com.gui.controller;

import java.util.Vector;

import com.SQLsupport.DBClass.Manufacturer;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.gui.Constants.*;

public class UserManufacturerController extends UserMenuController{

    private ObservableList<Manufacturer> list, selectableManufacturerList;
    boolean isHaveMark;

    @FXML
    private Button addMarkButton;

    @FXML
    private Button pieChartButton;

    @FXML
    private TextField markField;

    @FXML
    private TextField manufacturerField;

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
        isHaveMark=false;
        String path=!super.client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);

        list= FXCollections.observableArrayList();
        selectableManufacturerList=FXCollections.observableArrayList();

        idColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Integer>("id_manufacturer"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("country"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("email"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Double>("rating"));


        SelectAllManufacturers();

        this.initMainButtons();
    }

    public void SelectAllManufacturers(){
        super.client.sendDataToServer("select all manufacturer");
        super.client.sendDataToServer(" ");
        Vector<Manufacturer> manufacturers = super.client.receiveManufacturers();
        list.clear();
        list.addAll(manufacturers);
        manufacturerTable.setItems(list);
    }

    public void initMainButtons(){

        super.initMainButtons();

        manufacturerTable.getSelectionModel().selectedItemProperty().addListener(
                (obs,oldSelection,newSelection)->{
                    if(newSelection!=null){
                        selectableManufacturerList.clear();
                        selectableManufacturerList.add(manufacturerTable.getSelectionModel().getSelectedItem());
                        String selectableName = selectableManufacturerList.get(0).getName();
                        manufacturerField.setText(selectableName);
                    }
                }
        );

        pieChartButton.setOnMouseClicked(event -> {switchScene(event,PIE_CHART_FXML); });

        addMarkButton.setOnMouseClicked(event -> {
            if(isHaveMark)
               return;

            int mark=Integer.parseInt(markField.getText());
            if(mark>10||mark<0){
                markField.setText("Некорректно");
                return;
            }

            super.client.sendDataToServer("add mark to manufacturer");
            super.client.sendDataToServer(manufacturerField.getText()+"@@@"+markField.getText());
            if(super.client.receiveResult()){
                SelectAllManufacturers();
                isHaveMark=true;
            }


        });
    }


}
