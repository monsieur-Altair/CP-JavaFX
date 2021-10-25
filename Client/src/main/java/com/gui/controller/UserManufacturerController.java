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

public class UserManufacturerController {

    private OwnClient client;
    private ObservableList<Manufacturer> list;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button basketButton;

    @FXML
    private TableColumn<Manufacturer, String> countryColumn;

    @FXML
    private TableColumn<Manufacturer, String> emailColumn;

    @FXML
    private Label headLabel;

    @FXML
    private AnchorPane headerPane;

    @FXML
    private TableColumn<Manufacturer, Integer> idColumn;

    @FXML
    private Button languageButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button closeButton;

    @FXML
    private VBox leftPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button manufacturerButton;

    @FXML
    private TableView<Manufacturer> manufacturerTable;

    @FXML
    private TableColumn<Manufacturer, String> nameColumn;

    @FXML
    private Button productButton;

    @FXML
    private TableColumn<Manufacturer, Double> ratingColumn;

    @FXML
    private Button themeButton;

    @FXML
    void initialize() {
        client = OwnClient.getInstance();
        String path=!client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);
        list= FXCollections.observableArrayList();

        idColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Integer>("id_manufacturer"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("country"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("email"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Manufacturer,Double>("rating"));

        client.sendDataToServer("select all manufacturer");
        client.sendDataToServer(" ");
        Vector<Manufacturer> manufacturers= client.receiveManufacturers();
        list.clear();
        list.addAll(manufacturers);
        manufacturerTable.setItems(list);


        closeButton.setOnMouseClicked(event->{
            client.sendDataToServer("exit");
            stage = (Stage)closeButton.getScene().getWindow();
            stage.close();
        });

        themeButton.setOnMouseClicked((event)->{
            String path1=client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
            switchTheme(path1);
            client.switchTheme();
        });
    }

    public void switchTheme(String path){

        ObservableList<String> styleSheets=headerPane.getStylesheets();

        String css = MainMenuGUI.class.getResource(path).toExternalForm();
        styleSheets.add(css);

        if(styleSheets.size()>1)
            styleSheets.remove(0);

        headerPane.getStyleClass().add("header");
        mainPane.getStyleClass().add("main");
        leftPane.getStyleClass().add("left");

        manufacturerTable.getStyleClass().add("table-view");
        idColumn.getStyleClass().add("column-header-background");
        countryColumn.getStyleClass().add("column-header-background");
        ratingColumn.getStyleClass().add("column-header-background");
        emailColumn.getStyleClass().add("column-header-background");
        nameColumn.getStyleClass().add("column-header-background");
        headLabel.getStyleClass().add("label-header");
    }

}
