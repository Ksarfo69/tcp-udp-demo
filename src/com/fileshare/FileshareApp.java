package com.fileshare;

import com.fileshare.dto.Message;
import com.fileshare.interfaces.Command;
import com.fileshare.interfaces.Protocol;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileshareApp {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String in = "";
        byte[] payload;

        System.out.println("Welcome to fileShare. Please input your preferred protocol below: ");
        System.out.println("[TCP_CLIENT, TCP_SERVER, UDP_CLIENT, UDP_SERVER]");

        while(scanner.hasNext())
        {
            //get the message.
            in = scanner.nextLine();
            Command command = Command.parseCommand(in);

            switch (command)
            {
                case TCP_CLIENT -> TCPClientDriver.main(new String[0]);
                case TCP_SERVER -> TCPServerDriver.main(new String[0]);
                case UDP_CLIENT -> UDPClientDriver.main(new String[0]);
                case UDP_SERVER -> UDPServerClient.main(new String[0]);
            }
        }
    }
}
