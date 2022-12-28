package com.fileshare.models;

import com.fileshare.enums.Protocol;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.dto.Message;

import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Broker implements MessageBroker {
    private Socket socket;
    private DatagramSocket datagramSocket;

    public Broker() {}

    public void transmit(Message message) {
        InetAddress destination = message.destination();
        int port = message.port();
        byte[] payload = message.payload();

        if(message.protocol() == Protocol.TCP)
        {
            try {
                PrintWriter pr = new PrintWriter(this.socket.getOutputStream());
                String out = new String(payload, StandardCharsets.UTF_8).trim();
                pr.println(out);
                pr.flush();

                System.out.println("sent: "+ out);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            try{
                //create packet
                DatagramPacket packet = new DatagramPacket(payload, payload.length, destination, port);

                //send packet
                this.datagramSocket.send(packet);

                System.out.println("sent: "+ new String(packet.getData(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.datagramSocket = socket;
    }
}
