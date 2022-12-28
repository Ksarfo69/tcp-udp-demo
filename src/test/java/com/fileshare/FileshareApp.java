package com.fileshare;

import com.fileshare.enums.Command;

import java.util.Scanner;

public class FileshareApp {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Scanner scanner = new Scanner(System.in);
        String in = "";
        byte[] payload;

        System.out.println("Welcome to fileShare. Please select an option from below: ");
        System.out.println("[TCP_CLIENT, TCP_SERVER, UDP_CLIENT, UDP_SERVER]");

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
