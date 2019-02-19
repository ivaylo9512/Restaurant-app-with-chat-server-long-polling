package com.vision.project.controllers;

import com.vision.project.models.Chat;
import com.vision.project.models.DTOs.MessageDto;
import com.vision.project.models.Message;
import com.vision.project.models.Session;
import com.vision.project.models.UserDetails;
import com.vision.project.services.base.ChatService;
import com.vision.project.services.base.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chats")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping(value = "/getChats")
    public List<Chat> getChats(){
        return chatService.findUserChats(3);
    }

    @GetMapping(value = "/nextSessions")
    public List<Session> nextChatSessions(
            @RequestParam(name = "chatId") int chatId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "pageSize") int pageSize){

        return chatService.findNextChatSessions(1, 1, 1);
    }

    @GetMapping(value = "/getChatUpdates")
    public List<Message> chatUpdates(){
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        int userId = userDetails.getId();
        return chatService.getNewMessages(userId);
    }

    @PostMapping(value = "/newMessage")
    public Message newMessage(@RequestParam MessageDto message){
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        message.setSenderId(userDetails.getId());
        return chatService.addNewMessage(message);
    }
}