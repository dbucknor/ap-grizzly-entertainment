package project.grizzly.application.controllers;

import project.grizzly.application.models.Chat;
import project.grizzly.application.services.Client;
import project.grizzly.application.services.ThreadService;
import project.grizzly.server.Request;
import project.grizzly.server.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ChatsController {
    private List<Chat> chats;
    //    private final List<ChatsListener> listeners;
    private Chat currentChat;
    private Client client;
//
//    public ChatsController(Client client) {
//        this.client = client;
//        listeners = new ArrayList<>();
//    }
//
//    public ChatsController() {
//        listeners = new ArrayList<>();
//        client = Client.getInstance();
//    }
//
//    public void insertChat(Chat chat) {
////        client.sendAction("ADD CHAT");
////        client.send(chat);
//    }
//
//    public Chat readChat(String chatId) {
//        Future<Chat> future = ThreadService.executor.submit((Callable<Chat>) () -> {
//            Request r = new Request("READ", "CHAT", chatId);
//            client.sendRequest(r);
////            client.send(chatId);
//            return (Chat) ((Response) client.receiveResponse(r)).getValue();
//        });
//
//        try {
//            return future.get();
//        } catch (ExecutionException | InterruptedException e) {
//            return null;
//        }
//    }
//
//    public void refresh() {
//        readAllChats();
//    }
//
//    public void readAllChats() {
//        ThreadService.executor.submit(() -> {
////            client.getStreams();
////            client.sendAction("READ-ALL CHAT");
////            Object res = client.receiveResponse();
//////            client.closeStreams();
////            SwingUtilities.invokeLater(() -> {
////                chats = res != null ? (List<Chat>) res : new ArrayList<>();
////                updateListeners();
////            });
//        });
//    }
//
//    public void updateChat(Chat chat) throws Exception {
////        ThreadService.executor.submit(() -> {
////            client.sendAction("UPDATE CHAT");
////            client.send(chat);
////        });
////        crudService.update(chat);
//    }
//
//    public void deleteChat(Chat chat) {
//        ThreadService.executor.submit(() -> {
////            client.sendAction("DELETE CHAT");
////            client.send(chat.getChatId());//todo errors
//        });
//    }
//
//    public void deleteChat(String chatId) {
//        ThreadService.executor.submit(() -> {
////            client.sendAction("DELETE CHAT");
////            client.send(chatId);
//        });
//    }
//
//    public List<ChatsListener> getListeners() {
//        return listeners;
//    }
//
//    public List<Chat> getChats() {
//        return chats;
//    }
//
//    public Chat getCurrentChat() {
//        return currentChat;
//    }
//
//    public void setCurrentChat(Chat currentChat) {
//        this.currentChat = currentChat;
//    }
//
////    public CRUDService<Chat, String> getCrudService() {
////        return crudService;
////    }
////
////    public void setCrudService(CRUDService<Chat, String> crudService) {
////        this.crudService = crudService;
////    }
//
//    private void updateListeners() {
//        for (ChatsListener cl : listeners
//        ) {
//            if (cl != null) {
//                cl.onUpdate(chats);
//            }
//        }
//    }
//
//    public void addListener(ChatsListener chatsListener) {
//        listeners.add(chatsListener);
//    }
//
//    public void removeListener(ChatsListener chatsListener) {
//        listeners.remove(chatsListener);
//    }
}
