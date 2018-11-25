package pl.sdacademy.chat.client;

import java.io.IOException;

public class ClientTerminalMain  {





    public static void main(String[] args) {

        try {
            ClientTerminal clientTerminal = new ClientTerminal();
            Thread thread = new Thread(clientTerminal);
            thread.setName("terminal");
            thread.start();

            thread.join();
        } catch (IOException e) {
            System.out.print("Could not connect to server" + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Application was forcibly closed");
            e.printStackTrace();
        }
    }
}
