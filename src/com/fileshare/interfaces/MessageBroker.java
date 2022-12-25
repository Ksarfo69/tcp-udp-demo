package com.fileshare.interfaces;

import com.fileshare.dto.Message;

import java.net.DatagramSocket;
import java.net.Socket;

public interface MessageBroker {
    public void transmit(Message message);

    public void setSocket(Socket socket);
    public void setSocket(DatagramSocket socket);
}
