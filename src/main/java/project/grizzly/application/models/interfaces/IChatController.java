package project.grizzly.application.models.interfaces;

import project.grizzly.application.models.Message;

public interface IChatController {
    void insertMessage(Message message);

    void fetchMessages(String chatId) throws Exception;

    void deleteMessage(Message message);

    void deleteMessage(String messageId);


}
