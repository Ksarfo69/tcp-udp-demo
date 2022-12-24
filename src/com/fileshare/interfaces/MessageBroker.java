package com.fileshare.interfaces;

import com.fileshare.dto.Message;

public interface MessageBroker {
    public void transmit(Message message);
}
