package com.fileshare.models;

import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;
import com.fileshare.interfaces.Protocol;
import com.fileshare.dto.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class UPDServer extends Node {
    private DatagramSocket socket;
    private InetAddress address;
    private DatagramPacket packet;
    private int port;
    private MessageBroker messageBroker;

    public UPDServer(String address, int port)
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
        String out="";
        byte[] payload = new byte[65535];

        try{
            DatagramPacket packet = new DatagramPacket(payload, payload.length);

            //receive packet from sender
            socket.receive(packet);

            //read the data and trim out whitespaces
            out = new String(payload, StandardCharsets.UTF_8).trim();

            System.out.println("server received: " + out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(String out, String destinationIp, int destinationPort) {
        //close socket if message was exit
        if(out.equalsIgnoreCase("exit")){
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

    public void cleanup()
    {
        try{
            System.out.println("Cleaning up resources...");

            //close socket
            if(Objects.nonNull(this.socket))
            {
                this.socket.close();
                System.out.println("Socket closed.");
            }
            System.out.println("UDP_SERVER exited.");

            //kill thread.
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
