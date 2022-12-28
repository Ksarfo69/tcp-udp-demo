package com.fileshare.interfaces;

import java.net.DatagramSocket;
import java.net.Socket;

public abstract class Node {
    public void sendMessage(String out, String destinationIp, int port) {};

    public void receiveMessage() {};

    public void connect() {};

    public void cleanup() {} ;

    public Socket getSocket() {
        return null;
    };

    public DatagramSocket getDatagramSocket() {
        return null;
    };

}
