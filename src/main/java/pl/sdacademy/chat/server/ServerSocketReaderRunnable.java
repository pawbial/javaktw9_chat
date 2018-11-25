package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerSocketReaderRunnable implements Runnable {

    private final Socket client;
    private final ChatLog chatLog;

    public ServerSocketReaderRunnable(Socket client, ChatLog chatLog) {
        this.client = client;
        this.chatLog = chatLog;
    }

    @Override
    public void run() {
        boolean register = chatLog.register(client);
        if (!register) {
            return;
        }
        if (register) {
            try (ObjectInputStream connectionFromClient = new ObjectInputStream(client.getInputStream())) {
                ChatMessage userMessage;
                do {
                    userMessage = (ChatMessage) connectionFromClient.readObject();
                    if (userMessage.getMessage() == null || userMessage.getMessage().equalsIgnoreCase("exit")) {
                        chatLog.unregister(client);
                        break;
                    }
                    chatLog.acceptMessage(userMessage);
                }
                while (!userMessage.getMessage().equalsIgnoreCase("exit"));


            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("### Client disconnected due to network problems ###");
                chatLog.unregister(client);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("### Client disconnected due to invalid message format");
                chatLog.unregister(client);
            }

            //zarejestrować się w chatlogu
            //jeżeli się udało to pobierz OIS dla tego clienta, try with rescources
            //w pętli odczytywać komunikaty dopóki nie będzie "exit"
            //każdy komunikat przekaż do chatlogu, oprócz komunikatu exit
            //jeżeli pojawił się exit lub nie udało się odczytać komunikatu to wyrejestruj sie z czatlogu
        }
    }
}
