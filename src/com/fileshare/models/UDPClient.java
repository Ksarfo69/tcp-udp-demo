package com.fileshare.models;

import com.fileshare.interfaces.Node;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient implements Node {
    private InetAddress address;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;

    public UDPClient (String address, int port)
    {
        try {
            this.socket = new DatagramSocket(port);
            this.port = port;
            this.address = InetAddress.getByName(address);
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

            System.out.println("client received: " + out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(byte[] payload, Node node)
    {
        InetAddress destinationAddress = node.getAddress();
        int destinationPort = node.getPort();
        try{
            //create packet
            this.packet = new DatagramPacket(payload, payload.length, destinationAddress, destinationPort);

            //send packet
            socket.send(this.packet);

            System.out.println("client sent: "+ new String(this.packet.getData(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    public InetAddress getAddress()
    {
        return this.address;
    }

    public int getPort()
    {
        return this.port;
    }

    public DatagramSocket getSocket()
    {
        return this.socket;
    }
}
