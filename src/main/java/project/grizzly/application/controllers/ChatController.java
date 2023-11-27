package project.grizzly.application.controllers;

import project.grizzly.application.models.Chat;
import project.grizzly.application.models.Message;
import project.grizzly.application.models.User;
import project.grizzly.application.models.interfaces.IChatController;
import project.grizzly.application.services.Client;

import java.util.List;

public class ChatController {
//    private Chat chat;
//    private List<Message> messages;
//    private List<Message> listeners;
//    //    private CRUDService<Message, String> crudService;
//    private Client client;
//
//    private ChatsController chatsController;
//
//    public ChatController(Chat chat) {
//        this.chat = chat;
////        this.crudService = new CRUDService<>(Message.class);\
//        client = Client.getInstance();
////        fetchMessages(chat.getChatId());
//    }
//
//    @Override
//    public void insertMessage(Message message) {
////        crudService.insert(message);
////        client.getStreams();
////        client.sendAction("ADD MESSAGE");
////        client.send(message);
////        client.closeStreams();
////        refreshMessages();
//    }
//
//    @Override
//    public void fetchMessages(String chatId) {
//        try {
////            client.getStreams();
////            client.sendAction("READ-WHERE MESSAGE");
////            client.send(new CombinedQuery<Message>("SELECT ALL m FROM Message m")
////                    .where("m.chatId", "=:chatId", chatId)
////                    .orderBy("m.sentDate", "ASC"));
////
////            messages = (List<Message>) client.receiveResponse();
////
////            client.closeStreams();
//        } catch (Exception e) {
//            System.out.println("messages: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void deleteMessage(Message message) {
////        crudService.delete(message.getMessageId());
////        client.sendAction("DELETE MESSAGE");
////        client.send(message.getMessageId());
////        refreshMessages();
//
//    }
//
//    @Override
//    public void deleteMessage(String messageId) {
////        client.sendAction("DELETE MESSAGE");
////        client.send(messageId);
////        refreshMessages();
//    }
//
//    public Chat getChat() {
//        return chat;
//    }
//
//    public List<Message> getMessages() {
//        return messages;
//    }
//
//    public void refreshMessages() {
//        fetchMessages(chat.getChatId());
//    }
//
//    public void setChat(Chat chat) {
//        this.chat = chat;
//    }
//
//    public User getOtherUser(User currentUser) {
//        return chat.getUsers().stream().filter((u) -> u.getUserId().compareTo(currentUser.getUserId()) != 0).toList().get(0);
//    }
//
////    public CRUDService<Message, String> getCrudService() {
////        return crudService;
////    }
////
////    public void setCrudService(CRUDService<Message, String> crudService) {
////        this.crudService = crudService;
////    }
//
//
//    public ChatsController getChatsController() {
//        return chatsController;
//    }
//
//    public void setChatsController(ChatsController chatsController) {
//        this.chatsController = chatsController;
//    }
}
