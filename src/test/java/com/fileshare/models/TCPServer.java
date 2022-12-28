package com.fileshare.models;

import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;
import com.fileshare.interfaces.Protocol;
import com.fileshare.dto.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TCPServer extends Node {
    private Socket socket;
    private ServerSocket ss;
    private InetAddress address;
    private int port;
    private MessageBroker messageBroker;


    public TCPServer(String address, int port)
    {
        try {
            this.address = InetAddress.getByName(address);
            this.port = port;
            this.ss = new ServerSocket(port);
            this.messageBroker = new Broker();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void connect()
    {
        System.out.println("attempting to connect");
        try {
            this.socket = this.ss.accept();
            this.messageBroker.setSocket(this.socket);
            System.out.println("Connection established between client and server.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendMessage(String out, String destinationIp, int port) {
        //close socket if message was exit
        if(out.equalsIgnoreCase("exit")){
            System.out.println("Exiting TCP_SERVER...");
            cleanup();
            return;
        }

        try {
            //process message
            byte[] payload;
            InetAddress destination = InetAddress.getByName(destinationIp);
            payload = out.getBytes(StandardCharsets.UTF_8);

            Message message = new Message(destination, port, Protocol.TCP, payload);

            this.messageBroker.transmit(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void receiveMessage() {
        try {
            InputStreamReader input = new InputStreamReader(this.socket.getInputStream());
            BufferedReader br = new BufferedReader(input);

            String in = br.readLine();

            //close socket if message was exit
            if(in.equalsIgnoreCase("exit")) cleanup();

            System.out.println("server received: "+ in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void cleanup() {
        try{
            System.out.println("Cleaning up resources...");

            //close socket
            if(Objects.nonNull(this.socket))
            {
                this.socket.close();
                System.out.println("Socket closed.");
            }
            System.out.println("TCP_SERVER exited.");

            //kill thread.
            Thread.currentThread().interrupt();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
