package com.fileshare;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.Node;
import com.fileshare.interfaces.Protocol;
import com.fileshare.models.UDPClient;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClientDriver {
    public static void main(String[] args) {
        Node client = new UDPClient("localhost", 6100);

        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listener(client);
            }
        });


        //initial request for resource
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sender(client);
            }
        });

        clientThread.start();
        serverThread.start();
    }

    public static void listener(Node client)
    {
        while(!client.getDatagramSocket().isClosed())
        {
            client.receiveMessage();
        }
    }


    public static void sender(Node client)
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
                int port = 6200;
                payload = in.getBytes(StandardCharsets.UTF_8);

                Message message = new Message(destination, port, Protocol.UDP, payload);

                client.sendMessage(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
