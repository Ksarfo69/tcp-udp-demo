package com.fileshare.interfaces;

import com.fileshare.dto.Message;

import java.net.DatagramSocket;
import java.net.Socket;

public abstract class Node {
    public void sendMessage(Message message) {};

    public void receiveMessage() {};

    public void connect() {};

    public void closeSocket() {} ;

    public Socket getSocket() {
        return null;
    };

    public DatagramSocket getDatagramSocket() {
        return null;
    };

}
