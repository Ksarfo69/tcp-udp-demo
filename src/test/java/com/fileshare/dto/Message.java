package com.fileshare.dto;
import com.fileshare.interfaces.Protocol;

import java.net.InetAddress;

public record Message(
        InetAddress destination,
        int port,
        Protocol protocol,
        byte[] payload
) {
}
