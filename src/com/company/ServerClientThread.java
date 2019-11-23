package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClientThread extends Thread {
    Socket clientSocket;

    private PrintWriter out;
    private BufferedReader in;
    private int izabrani;
    private String plainText;
    private String cypherText;
    private Boolean b[];

    ServerClientThread(Socket s){
        this.clientSocket = s;
        b = new Boolean[2];
        for(int i=0;i<2;i++)
            b[i]= false;
    }

    private void printMenu(PrintWriter out){
        out.println("5");
        out.println("---- Menu: ----");
        out.println("1 -- Roman cypther");
        out.println("2 -- jednostruko premestanje");
        out.println("3 -- RSA ");
        out.println("exit -- Exit application");
    }
    private void defaultAnswer(String message){
        System.out.println("Poruka klienta je:" + message +". Client id:"+this.clientSocket.getPort());
        out.println(1);
        out.println("Nepoznata poruka.");
    }

    public void run(){
        System.out.println("Thread running.." + this.getName());
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client connected: " + clientSocket.getPort());

        while (true) {
            String message = null;
            try {
                message = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (message != null) {
                switch (message) {
                    case "opet":
                        if(!b[0] && b[1]){
                            b[1] = false;
                            b[0] = true;
                            printMenu(out);
                        }else
                            defaultAnswer(message);
                        break;
                    case "exit":
                        System.out.println("Client disconnected: " + clientSocket.getPort());
                        out.println("1");
                        out.println("Hvala vam.");
                        return;
                    case "menu":
                        if (!b[0] && !b[1]) {
                            printMenu(out);
                            b[0] = true;
                        }else
                            defaultAnswer(message);
                        break;
                    case "1":
                    case "2":
                    case "3":
                        if (!b[1] && b[0]) {
                            izabrani = Integer.valueOf(message);
                            b[1] = true;
                            out.println("1");
                            out.println("Unesite poruku za siforvanje.");
                        }else
                            defaultAnswer(message);
                        break;
                    default:
                        if (b[0] && b[1]) {
                            plainText = message;
                            Cypher cp = new Cypher();
                            switch (izabrani) {
                                case 1:
                                    cypherText = cp.substitute(plainText);
                                    break;
                                case 2:
                                    cypherText = cp.transpose(plainText);
                                    break;
                                case 3:
                                    cypherText = cp.RSA(plainText);
                                    break;
                            }
                            b[0]=false;
                            out.println("3");
                            out.println("Vasa sifrovana poruka je:");
                            out.println(cypherText);
                            out.println("Unesite opet za ponovno sifrovanje.");
                        } else
                            defaultAnswer(message);
                        continue;
                }
            }
        }
    }
}



