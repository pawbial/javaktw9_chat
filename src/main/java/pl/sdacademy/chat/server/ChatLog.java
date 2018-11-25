package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

public class ChatLog {

    private Map<Socket, ObjectOutputStream> registeredClients;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd: HH:mm:ss");

    public ChatLog(Map<Socket, ObjectOutputStream> registeredClients) {

    }

    public boolean register(Socket client) {
        try {
            ObjectOutputStream clientsOutputStream = new ObjectOutputStream(client.getOutputStream());
            registeredClients.put(client, clientsOutputStream);
            return true;
        } catch (IOException e) {
            System.out.println("### Someone tried to connect but was rejected ###");
            e.printStackTrace();
            return false;
        }

    }

    public boolean unregister(Socket client) {
        ObjectOutputStream connectionToClient = registeredClients.remove(client);
        if (connectionToClient != null) {
            try {
                connectionToClient.close();
                System.out.println("### " + client + " was removed from chat");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void acceptMessage(ChatMessage message) {
        DatedChatMessage userMessage = new DatedChatMessage(message);
        printMessage(userMessage);
        updateClients(userMessage);
    }

    private void updateClients(ChatMessage message) {

        Set<Map.Entry<Socket, ObjectOutputStream>> allEntries = registeredClients.entrySet();

        for (Map.Entry<Socket, ObjectOutputStream> entry : allEntries) {
            ObjectOutputStream connectionToClient = entry.getValue();
            try {
                connectionToClient.writeObject(message);
                connectionToClient.flush();
            } catch (IOException e) {
                unregister(entry.getKey());
            }
        }
    }

    private void printMessage(DatedChatMessage userMessage) {
        System.out.println("<" + dateFormatter.format(userMessage.getRecieveDate())
                + "> <" + userMessage.getAuthor()
                + "> <" + userMessage.getMessage() + ">"); ;
    }


}
