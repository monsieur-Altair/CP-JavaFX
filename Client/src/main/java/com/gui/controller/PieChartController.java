package com.gui.controller;

import com.SQLsupport.DBClass.InformationForPieChart;
import com.implementation.client.OwnClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.Vector;

import static com.gui.Constants.DARK_THEME_PATH;
import static com.gui.Constants.LIGHT_THEME_PATH;

public class PieChartController extends UserMenuController{

    ObservableList<PieChart.Data> dataList;

    @FXML
    private PieChart myPieChart;

    @FXML
    public void initialize(){
        client = OwnClient.getInstance();
        dataList= FXCollections.observableArrayList();

        super.initMainButtons();

        String path=!client.isDarkTheme()?LIGHT_THEME_PATH:DARK_THEME_PATH;
        switchTheme(path);

        client.sendDataToServer("select data for pie chart");
        client.sendDataToServer(" ");
        Vector<InformationForPieChart> informationForPieCharts = client.receiveDataForPieChart();

        for(var data:informationForPieCharts)
            dataList.add(new PieChart.Data(data.getManufName(),data.getManufCount()));

        myPieChart.setData(dataList);
    }
}
