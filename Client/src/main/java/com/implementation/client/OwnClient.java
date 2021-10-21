package com.implementation.client;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.Cleaner;
import java.net.Socket;
import java.util.Scanner;

public class OwnClient {
    private Socket client;
    private ObjectOutputStream output_stream;
    private ObjectInputStream input_stream;
    private Scanner scan;
    private static OwnClient ownClient=null;


    private OwnClient(String host, int port) {
        try {
            client = new Socket(host, port);
            output_stream = new ObjectOutputStream(client.getOutputStream());
            input_stream = new ObjectInputStream(client.getInputStream());
            scan = new Scanner(System.in);
            System.out.println("connection established...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static OwnClient getInstance(){
        if(ownClient==null){
            ownClient=new OwnClient("127.0.0.1",2525);
        }
        return ownClient;
    }

    public void sendDataToServer(String data){
        try {
            output_stream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Run() {/*throws IOException,ClassNotFoundException{
        System.out.print("~client: ");

        int size = scan.nextInt();
        output_stream.writeObject(size);

        int[] array=(int[])(input_stream.readObject());
        System.out.print("~server: sent array-> ");
        for(int value: array)
            System.out.print("\t"+value);
        System.out.println();*/
    }

    public void Close() throws IOException{
        output_stream.close();
        input_stream.close();
        client.close();
    }
}

