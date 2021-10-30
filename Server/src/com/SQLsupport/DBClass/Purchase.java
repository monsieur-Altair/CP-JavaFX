package com.SQLsupport.DBClass;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Purchase implements Serializable {
    private int id_purchase, product_cost;
    private String product_name, product_type, manufacturer_name;

    public Purchase(int id_purchase, String product_name, String product_type, int product_cost, String manufacturer_name) {
        this.id_purchase = id_purchase;
        this.product_name = product_name;
        this.product_type = product_type;
        this.product_cost = product_cost;
        this.manufacturer_name = manufacturer_name;
    }

    public void print(FileWriter writer){
        try{
            writer.write("id: "+id_purchase+"\nname: "+product_name+"\ntype: ");
            writer.write(product_type+"\ncost: "+product_cost+"\nmanufacturer: "+manufacturer_name+"\n\n");
        }catch (IOException e){
            System.out.printf("\ncannot write purchase on file\n");
            e.printStackTrace();
        }
    }

    public void printConsole(){
        System.out.println("\nman="+manufacturer_name+"\ncost="+product_cost);
    }

    public int getId_purchase() {
        return id_purchase;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public int getProduct_cost() {
        return product_cost;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setProduct_cost(int product_cost) {
        this.product_cost = product_cost;
    }
}
