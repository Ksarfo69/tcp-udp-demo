package com.fileshare;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.Node;
import com.fileshare.interfaces.Protocol;
import com.fileshare.models.TCPClient;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClientDriver {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to TCP_CLIENT");
        Node client = new TCPClient("localhost", 6400);

        Thread.currentThread().sleep(7000);

        Thread listenerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listen(client);
            }
        });

        Thread senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                send(client);
            }
        });

        listenerThread.start();
        senderThread.start();
    }

    public static void listen(Node client)
    {
        while(!client.getSocket().isClosed())
        {
            client.receiveMessage();
        }
    }

    public static void send(Node client)
    {
        String in = "";
        byte[] payload;

        while(scanner.hasNext())
        {
            //get the message.
            in = scanner.nextLine();

            try {
                //process message
                InetAddress destination = InetAddress.getByName("localhost");
                int port = 6400;
                payload = in.getBytes(StandardCharsets.UTF_8);

                Message message = new Message(destination, port, Protocol.TCP, payload);

                client.sendMessage(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
