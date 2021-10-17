package com.server;

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

    public ThreadForServer(ServerSocket serverSocket){

        try {
            client=serverSocket.accept();
            clientCount++;
            System.out.println("client "+clientCount+" is connected");
            input_stream = new ObjectInputStream(client.getInputStream());
            output_stream = new ObjectOutputStream(client.getOutputStream());
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
        try {
            int size = (int) input_stream.readObject();
            int min = 0;
            int max = size;
            System.out.println("~client: " + size);
            int[] array = new int[size];
            for (int i = 0; i < size; i++)
                array[i] = (int) (min + (Math.random() * (max - min)));
            Thread.sleep(1500);
            output_stream.writeObject(array);
            this.Release();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
