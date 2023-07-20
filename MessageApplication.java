import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageApplication {
    private Map<User, List<Message>> userMessages = new HashMap<>();
    private Map<User, List<User>> subscriptions = new HashMap<>();

    public void importData(List<String> relationshipData, List<String> messageData) {
        for (String relationship : relationshipData) {
            String[] users = relationship.split(" > ");
            User subscriber = new User(users[0].trim());
            User author = new User(users[1].trim());
            subscriptions.putIfAbsent(author, new ArrayList<>());
            subscriptions.get(author).add(subscriber);
        }

        for (String messageInfo : messageData) {
            String[] messageParts = messageInfo.split(": ");
            String authorUsername = messageParts[0];
            String messageContent = messageParts[1];
            User author = new User(authorUsername);
            Message message = new Message(author, messageContent);
            userMessages.putIfAbsent(author, new ArrayList<>());
            userMessages.get(author).add(message);
        }
    }

    public List<Message> getMessagesForUser(User user) {
        return userMessages.getOrDefault(user, new ArrayList<>());
    }

    public void publishMessage(User sender, String content) {
        Message message = new Message(sender, content);
        userMessages.putIfAbsent(sender, new ArrayList<>());
        userMessages.get(sender).add(message);

        List<User> subscribers = subscriptions.getOrDefault(sender, new ArrayList<>());
        for (User subscriber : subscribers) {
            userMessages.putIfAbsent(subscriber, new ArrayList<>());
            userMessages.get(subscriber).add(message);
        }
    }
}
