package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here

        String url = "localhost";
        int port = 8080;
        Scanner s = new Scanner(System.in);
        switch(args[0]){
            case "server":
                Server srv = new Server(port);
                srv.start();
                break;
            case "client":
                Client client = new Client(url, port);
                while(true) {
                    String msg = s.nextLine();
                    System.out.println("Poruka se salje...");
                    client.printResponse(client.sendMessage(msg));
                    if(msg == "exit")
                        break;
                }
                break;
            default:
                System.out.println("Start is either as server or client.");
                break;
        }
    }
}
