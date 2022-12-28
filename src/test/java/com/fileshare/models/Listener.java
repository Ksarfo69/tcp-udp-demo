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
            //if client flag not set yet
            if(Objects.isNull(node.isActive()))
            {
                continue;
            }
            else if(node.isActive())
            {
                node.receiveMessage();
            }
            else
            {
                Thread.currentThread().interrupt();
            }
        }
    }
}
