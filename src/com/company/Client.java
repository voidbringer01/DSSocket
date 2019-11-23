package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String url, int port)  {
        try {
            this.client = new Socket(url, port);
            System.out.println("You have connected :");
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            int response = sendMessage("menu");
            printResponse(response);
        }
        catch(IOException io){
            System.out.println("Neuspesna konekcija.");
            System.exit(-1);
        }
    }

    public void printResponse(int response) throws IOException {
        String s = null;
        for(int i=0;i<response;i++) {
            s = in.readLine();
            System.out.println(s);
        }
    }

    public int sendMessage(String msg) throws IOException {
        out.println(msg);
        String response = in.readLine();
        System.out.println("Odgovor servera je: ");
        return Integer.valueOf(response);
    }
}
