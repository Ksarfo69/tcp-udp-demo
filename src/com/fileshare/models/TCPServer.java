package com.fileshare.models;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Node{
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
    public void sendMessage(Message message) {
        this.messageBroker.transmit(message);
    }

    @Override
    public void receiveMessage() {
        try {
            InputStreamReader input = new InputStreamReader(this.socket.getInputStream());
            BufferedReader br = new BufferedReader(input);

            String in = br.readLine();

            //close socket if message was exit
            if(in.equalsIgnoreCase("exit")) closeSocket();

            System.out.println("server received: "+ in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void closeSocket() {
        try{
            this.socket.close();
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
