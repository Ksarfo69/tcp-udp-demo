package com.fileshare;

import com.fileshare.interfaces.Node;
import com.fileshare.models.Listener;
import com.fileshare.models.Talker;
import com.fileshare.models.UPDServer;

import java.util.Scanner;

public class UDPServerClient {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int serverPort = 6200;

        System.out.println("Welcome to UDP_SERVER");
        System.out.println("Please type the ip and port of the client in the format xxx.xxx.xxx.xxx:xxxx: ");

        //parse ip and port
        String[] ipAndPort = scanner.nextLine().split(":");
        String destinationIp = ipAndPort[0];
        int destinationPort = Integer.parseInt(ipAndPort[1]);

        Node server = new UPDServer("localhost", serverPort);
        System.out.println("UDP_SERVER running on port " + serverPort);

        //create listener
        Listener listener = new Listener(server);

        //create talker
        Talker talker = new Talker(server, destinationIp, destinationPort);

        listener.start();
        talker.start();
    }
}
