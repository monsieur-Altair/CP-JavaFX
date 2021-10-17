package com.implementation.client;

public class Main {
    public static void main(String[] args) {
        final String HOST="127.0.0.1";
        final int PORT=2525;
        try{
            OwnClient client=new OwnClient(HOST,PORT);
            client.Run();
            client.Close();
        }catch(Exception exp) {
            exp.printStackTrace();
        }
    }
}
