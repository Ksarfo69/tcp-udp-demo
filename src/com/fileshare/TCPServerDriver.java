package com.fileshare;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;
import com.fileshare.interfaces.Protocol;
import com.fileshare.models.Broker;
import com.fileshare.models.TCPServer;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPServerDriver {
    public static void main(String[] args) {
        Node server = new TCPServer("localhost", 6400);
        MessageBroker messageBroker = new Broker();

        //establish handshake
        server.connect();

        Thread listenerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listen(server);
            }
        });

        Thread senderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                send(server);
            }
        });

        listenerThread.start();
        senderThread.start();
    }

    public static void listen(Node server)
    {
        while(!server.getSocket().isClosed())
        {
            server.receiveMessage();
        }
    }

    public static void send(Node server)
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
                int port = 6400;
                payload = in.getBytes(StandardCharsets.UTF_8);

                Message message = new Message(destination, port, Protocol.TCP, payload);

                server.sendMessage(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
