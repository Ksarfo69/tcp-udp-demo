package com.fileshare;

import com.fileshare.interfaces.Node;
import com.fileshare.models.Listener;
import com.fileshare.models.TCPClient;
import com.fileshare.models.Talker;

import java.util.Scanner;

public class TCPClientDriver {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int port = 6400;

        System.out.println("Welcome to TCP_CLIENT");
        System.out.println(Thread.currentThread().getName());
        System.out.println("Please type the ip and port of the destination server in the format xxx.xxx.xxx.xxx: ");

        //parse ip and port
        String destinationIp = scanner.nextLine();

        //create client
        Node client = new TCPClient("localhost", port);
        System.out.println("TCP_CLIENT running on port " + port);

        //create listener
        Listener listener = new Listener(client);

        //create talker
        Talker talker = new Talker(client, destinationIp, port);

        listener.start();
        talker.start();
    }

}
