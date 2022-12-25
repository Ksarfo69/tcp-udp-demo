package com.fileshare;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.Node;
import com.fileshare.interfaces.Protocol;
import com.fileshare.models.UPDServer;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPServerClient {
    public static void main(String[] args) {
        UDPTransfer();
    }


    public static void UDPTransfer()
    {
        Node server = new UPDServer("localhost", 6200);

        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listener(server);
            }
        });


        //initial request for resource
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sender(server);
            }
        });

        clientThread.start();
        serverThread.start();
    }

    public static void listener(Node server)
    {
        while(!server.getDatagramSocket().isClosed())
        {
            server.receiveMessage();
        }
    }


    public static void sender(Node server)
    {
        Scanner scanner = new Scanner(System.in);
        String in = "";
        byte[] payload;

        while(scanner.hasNext())
        {
            //get the message.
            in = scanner.nextLine();

            try {
                //process message
                InetAddress destination = InetAddress.getByName("localhost");
                int port = 6100;
                payload = in.getBytes(StandardCharsets.UTF_8);

                Message message = new Message(destination, port, Protocol.UDP, payload);

                server.sendMessage(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
