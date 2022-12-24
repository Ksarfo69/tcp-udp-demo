package com.fileshare.dto;
import com.fileshare.interfaces.Node;

public record Message(
        Node sender,
        Node receiver,
        byte[] payload
) {
}
