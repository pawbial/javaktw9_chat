package pl.sdacademy.chat.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ClientTerminalMain {


    public static void main(String[] args) throws InterruptedException {
        int reconnectionAttempts = 3;
        long secondsBetweenReconnectAttempt = 10;
        System.out.println("Welcome to Awesome Chat!");
        while (reconnectionAttempts > 0) {
            try {
                System.out.println("Attempting to connect to server");
                System.out.println();
                ClientTerminal clientTerminal = new ClientTerminal();
                Thread thread = new Thread(clientTerminal);
                thread.setName("terminal");
                thread.start();

                thread.join();
            } catch (IOException e) {
                System.out.println("Could not connect to server");
                System.out.println();
                reconnectionAttempts--;
                if (reconnectionAttempts > 0) {
                    countdown((int) secondsBetweenReconnectAttempt);
                }
            }
        }
        System.out.println("See you soon!");
    }

    private static void countdown(int second) throws InterruptedException {
        while (second > 0) {
            System.out.println("### Waiting " + second + " till reconnect attempt ... ");
            Thread.sleep(1000);
            second--;

        }
    }
}
