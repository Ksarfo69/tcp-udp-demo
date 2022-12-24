package com.fileshare.interfaces;

import java.net.DatagramSocket;
import java.net.InetAddress;

public interface Node {
    public void sendMessage(byte[] payload, Node node);

    public void receiveMessage();

    public InetAddress getAddress();

    public DatagramSocket getSocket();

    public int getPort();

    public void closeSocket() ;

}
