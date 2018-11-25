package pl.sdacademy.chat.model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DatedChatMessageTest {


    @Test
    public void testNameToChange() {
    // Given
        DatedChatMessage testMessage = new DatedChatMessage(new ChatMessage("AA","BB"));

    // When
        String author = testMessage.getAuthor();
        String message = testMessage.getMessage();
        LocalDateTime testDate = testMessage.getRecieveDate();
        LocalDateTime now = LocalDateTime.now();
        boolean dateResult = now.equals(testDate);

    // Then
        Assert.assertEquals("AA",author);
        Assert.assertEquals("BB",message);
        Assert.assertNotEquals(now,testDate);



    }


}