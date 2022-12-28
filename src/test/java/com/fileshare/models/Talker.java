package com.fileshare.models;

import com.fileshare.interfaces.Node;

import java.util.Scanner;

public class Talker extends Thread{
    private Node node;
    private Scanner scanner;
    private String destinationIp;
    private int port;

    public Talker(Node node, String destinationIp, int port)
    {
        this.node = node;
        this.destinationIp = destinationIp;
        this.port = port;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        talk();
    }

    private void talk()
    {
        String out = "";
        //System.out.println("Please type message below: ");
        while(!Thread.currentThread().isInterrupted() && scanner.hasNext())
        {
            //get the message.
            out = scanner.nextLine();
            node.sendMessage(out, destinationIp, port);
        }
    }
}
