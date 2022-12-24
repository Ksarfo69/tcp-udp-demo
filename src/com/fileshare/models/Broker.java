package com.fileshare.models;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;

public class Broker implements MessageBroker {

    public Broker() {}

    public void transmit(Message message) {
       Node receiver = message.receiver();
       Node sender = message.sender();

       sender.sendMessage(message.payload(), receiver);
    }
}
