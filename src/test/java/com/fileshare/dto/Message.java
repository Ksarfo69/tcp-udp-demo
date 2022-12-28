package com.fileshare.dto;
import com.fileshare.enums.Protocol;

import java.net.InetAddress;

public record Message(
        InetAddress destination,
        int port,
        Protocol protocol,
        byte[] payload
) {
}
