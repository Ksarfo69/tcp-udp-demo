package com.fileshare.models;

import com.fileshare.enums.Protocol;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;
import com.fileshare.dto.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TCPClient implements Node {
    private Socket socket;
    private InetAddress address;
    private int port;
    private MessageBroker messageBroker;
    private Boolean isActive;


    public TCPClient(String address, int port)
    {
        try {
            this.address = InetAddress.getByName(address);
            this.port = port;
            this.messageBroker = new Broker();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        establishConnection(0, 3);
    }

    public void establishConnection(int count , int maxTries)
    {
        if(count>=maxTries) {
            System.out.println("Could not connect, exiting");
            cleanup();
        }
        else {
            try {
                //create socket
                this.socket = new Socket(this.address, this.port);
                this.messageBroker.setSocket(this.socket);
                this.isActive = true;
                System.out.println("TCP_CLIENT running on port " + port);
            } catch (IOException e) {
                try {
                    System.out.println("Connection failed, retrying in 3 seconds. " + (maxTries-count - 1) + " attempt(s) remaining.");
                    Thread.sleep(3000);
                    establishConnection(count+1, maxTries);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }

    @Override
    public void sendMessage(String out, String destinationIp, int port) {
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

    @Override
    public void receiveMessage() {
        try {
            InputStreamReader input = new InputStreamReader(this.socket.getInputStream());
            BufferedReader br = new BufferedReader(input);

            String in = br.readLine();

            System.out.println("client received: "+ in);
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

            //set active flag to false;
            this.isActive = false;

            //kill thread.
            Thread.currentThread().interrupt();
            System.out.println("TCP_CLIENT exited.");
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Boolean isActive()
    {
        return this.isActive;
    }
}
