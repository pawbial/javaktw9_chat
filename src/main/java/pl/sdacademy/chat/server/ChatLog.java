package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ChatLog {

    private Map<Socket, ObjectOutputStream> registeredClients;

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
        ObjectOutputStream remove = registeredClients.remove(client);
        if (remove != null) {
            System.out.println("### " + client + " was removed from chat");
            return true;
        }
        return false;
    }

    public void acceptMessage(ChatMessage message) {
        DatedChatMessage userMessage = new DatedChatMessage(message);
        String messageToSend = "<" + userMessage.getRecieveDate() + "> <" + userMessage.getAuthor() + "> <" + userMessage.getMessage() + ">";
        System.out.println(messageToSend);
        for (Socket client : registeredClients.keySet())
            try {
                ObjectOutputStream objectOutputStream = registeredClients.get(client);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            } catch (IOException e) {
                registeredClients.remove(client);
                e.printStackTrace();
            }


        //przekonwertować ChatMessage na DatedChattMessage
        //wypisać na ekran wiadomość w formacie <Data><autor><message>
        //wysłać DatedChatMessage do wszystkich klientów
        //jeżeli nie udało się wysłać komunikatu do któregoś z klientów to wyrejestruj tego klienta
    }
}
