package pl.sdacademy.chat.model;

import java.time.LocalDateTime;

public class DatedChatMessage extends ChatMessage {
    public static final long serialVersionUID = 1L;
    private final LocalDateTime recieveDate;


    public DatedChatMessage(ChatMessage message) {
        super(message.getAuthor(),message.getMessage());
        recieveDate = LocalDateTime.now();


    }

    public LocalDateTime getRecieveDate() {
        return recieveDate;
    }
}
