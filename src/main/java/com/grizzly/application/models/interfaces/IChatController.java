package com.grizzly.application.models.interfaces;

import com.grizzly.application.models.Message;

public interface IChatController {
    void insertMessage(Message message);

    void fetchMessages(String chatId) throws Exception;

    void deleteMessage(Message message);

    void deleteMessage(String messageId);


}
