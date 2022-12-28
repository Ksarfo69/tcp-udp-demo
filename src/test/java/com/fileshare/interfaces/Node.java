package com.fileshare.interfaces;

public interface Node {
    public void sendMessage(String out, String destinationIp, int port);

    public void receiveMessage();

    public void cleanup();

    public Boolean isActive();
}
