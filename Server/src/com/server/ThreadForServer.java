package com.server;

import com.SQLsupport.DBClass.Manufacturer;
import com.SQLsupport.DBClass.User;
import com.SQLsupport.strategies.AddUser;
import com.SQLsupport.DBConnection;
import com.SQLsupport.Updatable;
import com.SQLsupport.strategies.SelectManufacturer;
import com.SQLsupport.strategies.SelectUser;

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
                Updatable sqlUpdate=null;
                switch (clientChoice){
                    case "registration":
                        sqlUpdate=new AddUser();
                        break;
                    case"exit":
                        try {
                            System.out.println("client "+currentClient+" disconnected");
                            allClientCount--;
                            this.Release();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    default:
                        break;
                }

                String dataFromClient=(String)input_stream.readObject();
                if(sqlUpdate!=null){
                    sqlUpdate.getData(dataFromClient);
                    boolean res = sqlUpdate.executeUpdate(dbConnection.getMyConnection());
                    output_stream.writeObject(res);
                }

                //check all select requests
                switch (clientChoice){
                    case "signIn":
                        var sqlSelect=new SelectUser();
                        sqlSelect.getData(dataFromClient);
                        Vector<User> user = sqlSelect.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(user);
                        break;
                    case "select all manufacturer":
                        var sqlSelect1=new SelectManufacturer();
                        Vector<Manufacturer> manufacturers = sqlSelect1.executeSelect(dbConnection.getMyConnection());
                        output_stream.writeObject(manufacturers);
                        break;
                }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
