import com.fileshare.dto.Message;
import com.fileshare.interfaces.MessageBroker;
import com.fileshare.interfaces.Node;
import com.fileshare.models.Broker;
import com.fileshare.models.UDPClient;
import com.fileshare.models.UPDServer;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UDPTransfer();
    }


    public static void UDPTransfer()
    {
        Node client = new UDPClient("localhost", 6100);
        Node server = new UPDServer("localhost", 6200);
        MessageBroker messageBroker = new Broker();

        //initial request for resource
        Message clientRequestMessage = new Message(client, server, "I want information".getBytes(StandardCharsets.UTF_8));
        messageBroker.transmit(clientRequestMessage);


        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                clientListener(client);
            }
        });


        //initial request for resource
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                serverSender(client, server, messageBroker);
            }
        });

        clientThread.start();
        serverThread.start();
    }

    public static void clientListener(Node client)
    {
        while(!client.getSocket().isClosed())
        {
            client.receiveMessage();
        }
    }


    public static void serverSender(Node client, Node server, MessageBroker messageBroker)
    {
        Scanner scanner = new Scanner(System.in);
        String in = "";
        byte[] payload;

        while(scanner.hasNext())
        {
            //get the message.
            in = scanner.nextLine();

            //close socket if message was exit
            if(in.equalsIgnoreCase("exit")) {client.closeSocket(); server.closeSocket(); break;}

            try {
                //populate message payload and write to packet.
                payload = in.getBytes(StandardCharsets.UTF_8);

                //process message
                Message message = new Message(server, client, payload);
                messageBroker.transmit(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
