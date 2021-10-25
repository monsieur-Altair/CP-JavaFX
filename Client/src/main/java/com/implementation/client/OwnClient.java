package com.implementation.client;



import com.SQLsupport.DBClass.Manufacturer;
import com.SQLsupport.DBClass.Product;
import com.SQLsupport.DBClass.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class OwnClient {
    private Socket client;
    private ObjectOutputStream output_stream;
    private ObjectInputStream input_stream;
    private Scanner scan;
    private static OwnClient ownClient=null;
    private User user=null;
    private boolean isRussianLanguage;
    private boolean isDarkTheme;

    public boolean isRussianLanguage() {
        return isRussianLanguage;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    private OwnClient(String host, int port) {
        try {
            client = new Socket(host, port);
            output_stream = new ObjectOutputStream(client.getOutputStream());
            input_stream = new ObjectInputStream(client.getInputStream());
            scan = new Scanner(System.in);
            isDarkTheme=true;
            isRussianLanguage=true;
            System.out.println("connection established...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void switchLanguage(){
        isRussianLanguage=!isRussianLanguage;
    }

    public void switchTheme(){
        isDarkTheme=!isDarkTheme;
    }



    public static OwnClient getInstance(){
        if(ownClient==null){
            ownClient=new OwnClient("127.0.0.1",2525);
        }
        return ownClient;
    }

    public void setUserProfile(User us){
        if(user==null){
            user=new User(us);
        }
    }

    public User getUserProfile(){
        return user;
    }

    public void sendDataToServer(String data){
        try {
            output_stream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean receiveResult(){
        try {
            return (boolean)input_stream.readObject();
        }catch (IOException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public Vector<User> receiveUsers(){
        try {
            return (Vector<User>) input_stream.readObject();
        }catch (IOException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public Vector<Manufacturer> receiveManufacturers(){
        try {
            return (Vector<Manufacturer>) input_stream.readObject();
        }catch (IOException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void Close() throws IOException{
        output_stream.close();
        input_stream.close();
        client.close();
    }

    public Vector<Product> receiveProducts() {
        try {
            return (Vector<Product>) input_stream.readObject();
        }catch (IOException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}

