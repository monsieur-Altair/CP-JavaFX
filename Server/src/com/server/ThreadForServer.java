package com.server;

import com.SQLsupport.DBClass.Manufacturer;
import com.SQLsupport.DBClass.Product;
import com.SQLsupport.DBClass.User;
import com.SQLsupport.SelectableProduct;
import com.SQLsupport.strategies.*;
import com.SQLsupport.DBConnection;
import com.SQLsupport.Updatable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ThreadForServer implements Runnable{
    private Socket client;
    private ObjectInputStream input_stream;
    private ObjectOutputStream output_stream;
    private static int allClientCount = 0;
    private int currentClient;
    private DBConnection dbConnection;

    public ThreadForServer(ServerSocket serverSocket, DBConnection dbConnection){

        try {
            client=serverSocket.accept();
            allClientCount++;
            currentClient=allClientCount;
            System.out.println("client " +currentClient+" connected");
            input_stream = new ObjectInputStream(client.getInputStream());
            output_stream = new ObjectOutputStream(client.getOutputStream());
            this.dbConnection=dbConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Release() throws IOException, NullPointerException {
        input_stream.close();
        output_stream.close();
        client.close();
    }

    @Override
    public void run() {
        while(true){
            try{
                String clientChoice=(String)input_stream.readObject();
                String dataFromClient=(String)input_stream.readObject();

                Updatable sqlUpdate=null;
                switch (clientChoice) {
                    case "registration" -> sqlUpdate = new AddUser();
                    case "exit" -> {
                        try {
                            System.out.println("client " + currentClient + " disconnected");
                            allClientCount--;
                            this.Release();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    default -> {}
                }
                if(sqlUpdate!=null){
                    sqlUpdate.getData(dataFromClient);
                    boolean res = sqlUpdate.executeUpdate(dbConnection.getMyConnection());
                    output_stream.writeObject(res);
                }


                //check all select requests
                switch (clientChoice) {
                    case "signIn" -> {
                        var sqlSelect = new SelectUser();
                        sqlSelect.getData(dataFromClient);
                        Vector<User> user = sqlSelect.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(user);
                    }
                    case "select all manufacturer" -> {
                        var sqlSelect1 = new SelectAllManufacturers();
                        Vector<Manufacturer> manufacturers = sqlSelect1.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(manufacturers);
                    }
                }


                //all selects of products
                SelectableProduct sqlSelect2=null;
                switch (clientChoice) {
                    case "select all products" -> sqlSelect2 = new SelectAllProducts();
                    case "select one product" -> sqlSelect2 = new SelectOneProduct();
                    case "select by manufacturer" -> sqlSelect2 = new SelectProductsByManufacturer();
                }
                if(sqlSelect2!=null){
                    sqlSelect2.getData(dataFromClient);
                    Vector<Product> product = sqlSelect2.executeSelect(dbConnection.getMyConnection());
                    output_stream.writeObject(product);
                }

            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
