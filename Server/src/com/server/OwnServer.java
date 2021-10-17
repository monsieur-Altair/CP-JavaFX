package com.server;

import java.io.IOException;
import java.net.ServerSocket;

public class OwnServer {

    private ServerSocket serverSocket;
    static OwnServer oneServer=null;
    private OwnServer(){
        final int PORT = 2525;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OwnServer createOwnServer(){
        if(oneServer==null)
            oneServer=new OwnServer();
        return oneServer;
    }

    public void run() {
        while (true)
            new Thread(new ThreadForServer(serverSocket)).start();
    }

    public void close(){
        try{
            serverSocket.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
