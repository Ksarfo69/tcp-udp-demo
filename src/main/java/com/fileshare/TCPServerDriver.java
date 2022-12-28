package com.fileshare;

import com.fileshare.interfaces.Node;
import com.fileshare.models.Listener;
import com.fileshare.models.TCPServer;
import com.fileshare.models.Talker;

import java.util.Scanner;

public class TCPServerDriver {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int port = 6400;

        System.out.println("Welcome to TCP_SERVER");
        System.out.println("Please type the ip and port of the client in the format xxx.xxx.xxx.xxx:xxxx: ");

        //parse ip and port
        String destinationIp = scanner.nextLine();

        Node server = new TCPServer("localhost", port);

        //create listener
        Listener listener = new Listener(server);

        //create talker
        Talker talker = new Talker(server, destinationIp, port);

        //TCP protocol will interrupt main thread if handshake fails
        if(!Thread.currentThread().isInterrupted())
        {
            listener.start();
            talker.start();
        }
    }
}
