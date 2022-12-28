package com.fileshare;

import com.fileshare.interfaces.Node;
import com.fileshare.models.Listener;
import com.fileshare.models.Talker;
import com.fileshare.models.UDPClient;

import java.util.Scanner;

public class UDPClientDriver {
    private static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int clientPort = 6100;

        System.out.println("Welcome to UDP_CLIENT");
        System.out.println("Please type the ip and port of the destination server in the format xxx.xxx.xxx.xxx:xxxx: ");

        //parse ip and port
        String[] ipAndPort = scanner.nextLine().split(":");
        String destinationIp = ipAndPort[0];
        int destinationPort = Integer.parseInt(ipAndPort[1]);

        Node client = new UDPClient("localhost", clientPort);
        System.out.println("UDP_CLIENT running on port " + clientPort);

        //create listener
        Listener listener = new Listener(client);

        //create talker
        Talker talker = new Talker(client, destinationIp, destinationPort);

        listener.start();
        talker.start();
    }
}
