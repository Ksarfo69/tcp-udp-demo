package com.fileshare.enums;

public enum Command {
    TCP_CLIENT,
    TCP_SERVER,
    UDP_CLIENT,
    UDP_SERVER;

    public static Command parseCommand(String input)
    {
        Command command = null;

        switch (input)
        {
            case "TCP_CLIENT" -> command =  Command.TCP_CLIENT;
            case "UDP_CLIENT" -> command =  Command.UDP_CLIENT;
            case "TCP_SERVER" -> command =  Command.TCP_SERVER;
            case "UDP_SERVER" -> command =  Command.UDP_SERVER;
        }

        return command;
    }
}
