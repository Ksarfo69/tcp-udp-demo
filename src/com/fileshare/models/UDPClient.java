package com.fileshare.models;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient extends Node {
    private InetAddress address;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    private MessageBroker messageBroker;

    public UDPClient (String address, int port)
    {
        try {
            this.socket = new DatagramSocket(port);
            this.port = port;
            this.address = InetAddress.getByName(address);
            this.messageBroker = new Broker();
            this.messageBroker.setSocket(this.socket);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void receiveMessage()
    {
        String in="";
        byte[] payload = new byte[65535];

        try{
            DatagramPacket packet = new DatagramPacket(payload, payload.length);

            //receive packet from sender
            socket.receive(packet);

            //read the data and trim out whitespaces
            in = new String(payload, StandardCharsets.UTF_8).trim();

            //close socket if message was exit
            if(in.equalsIgnoreCase("exit")) closeSocket();

            System.out.println("client received: " + in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(Message message)
    {
        this.messageBroker.transmit(message);
    }

    public void closeSocket()
    {
        try{
            this.socket.close();
            Thread.currentThread().interrupt();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public DatagramSocket getDatagramSocket() {
        return socket;
    }
}
