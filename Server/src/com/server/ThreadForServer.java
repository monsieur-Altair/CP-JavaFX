package com.server;

import com.SQLsupport.AddUser;
import com.SQLsupport.DBConnection;
import com.SQLsupport.Requestable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadForServer implements Runnable{
    private Socket client;
    private ObjectInputStream input_stream;
    private ObjectOutputStream output_stream;
    private static int clientCount=0;
    private DBConnection dbConnection;

    public ThreadForServer(ServerSocket serverSocket, DBConnection dbConnection){

        try {
            client=serverSocket.accept();
            clientCount++;
            System.out.println("client "+clientCount+" is connected");
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

        try{
            String clientChoice=(String)input_stream.readObject();
            Requestable sqlRequest;
            switch (clientChoice){
                case "registration":
                    sqlRequest=new AddUser();
                    String dataFromClient=(String)input_stream.readObject();
                    sqlRequest.getData(dataFromClient);
                    sqlRequest.execute(dbConnection.getMyConnection());
                    break;
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            this.Release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
