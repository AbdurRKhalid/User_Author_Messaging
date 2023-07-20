import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "programming-challenge-input.txt"; // Replace with the path to your input file

        List<String> relationshipData = new ArrayList<>();
        List<String> messageData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(">")) {
                    relationshipData.add(line.trim());
                } else if (line.contains(":")) {
                    messageData.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Create and initialize the application
        MessageApplication messageApp = new MessageApplication();
        messageApp.importData(relationshipData, messageData);

        // Use case A: Fetch messages for a certain user
        User targetUser = new User("Cristina Bush");
        List<Message> userMessages = messageApp.getMessagesForUser(targetUser);
        System.out.println("Messages for user " + targetUser.getUsername() + ":");
        for (Message message : userMessages) {
            System.out.println(message.getContent());
        }

        // Use case B: Publish a message
        User sender = new User("Toby Fisher");
        String content = "Toby Fisher: One for all and all for one, Muskehounds are always ready...";
        messageApp.publishMessage(sender, content);

        // After publishing, get messages for Stella Carpenter (who is subscribed to Toby Fisher)
        targetUser = new User("Stella Carpenter");
        userMessages = messageApp.getMessagesForUser(targetUser);
        System.out.println("\nMessages for user " + targetUser.getUsername() + ":");
        for (Message message : userMessages) {
            System.out.println(message.getContent());
        }
    }
}
