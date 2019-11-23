package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private ServerSocket srv;
    private Socket clientSocket;
    private int port;

    public Server(int port) {
        this.port = port;

    }
    public void run() {
        try {
            srv = new ServerSocket(port);
            System.out.println("Server running on port: " + port);
        }
        catch(IOException e){
            System.out.println(e);
        }

        while(true) {
            try {
                clientSocket = srv.accept();
            } catch (IOException io) {
                System.out.println(io);
            }
            ServerClientThread sct = new ServerClientThread(clientSocket);
            sct.start();
        }

    }
}
