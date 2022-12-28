package com.fileshare.models;

import com.fileshare.interfaces.Node;

import java.util.Objects;

public class Listener extends Thread{
    private Node node;

    public Listener(Node node)
    {
        this.node = node;
    }
    @Override
    public void run() {
        listen();
    }

    private void listen()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            if(Objects.isNull(node.getSocket()) || node.getSocket().isClosed())
            {
                Thread.currentThread().interrupt();
            }
            else
            {
                node.receiveMessage();
            }
        }
    }
}
