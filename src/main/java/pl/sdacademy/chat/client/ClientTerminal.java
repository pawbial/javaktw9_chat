package pl.sdacademy.chat.client;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTerminal implements Runnable {

    private final Socket connectionToServer;

    public ClientTerminal() throws IOException {
        connectionToServer = new Socket("IP", 5567);
    }


    @Override
    public void run() {
        try (OutputStream streamToServer = connectionToServer.getOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(streamToServer)) {
                System.out.print("> Your nickname");
                Scanner userInput = new Scanner(System.in);
                String author = userInput.nextLine();
                String message = userInput.nextLine();
                String exit = "exit";
                while (!message.equalsIgnoreCase(exit)) {
                    System.out.print(">");
                    String userMessage = userInput.nextLine();
                    ChatMessage chatMessage = new ChatMessage(author, userMessage);
                    objectOutputStream.writeObject(chatMessage);
                    objectOutputStream.flush();
                }

            } catch (IOException e) {
                System.out.println("message error");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("message error");
            e.printStackTrace();
        }
        //wytworzsenie strumieni >> try with rescources
        //pobierz nick od usera
        //pętla aż user wpisze exit
        //w pętli pobiera text do wysłania od użytkownika
        // wysyłamy go do servera >> wpisujemy do OOS
        //
    }
}
