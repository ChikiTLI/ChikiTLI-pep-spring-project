package com.example.service;

import com.example.exception.*;
import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).get();
    }

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getUserMessages(Integer postedBy) {
        return messageRepository.findMessagesByPostedBy(postedBy);
    }

    public int updateMessage(Integer id, Message newMessage) throws IllegalArgumentException {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            Message oldMessage = optionalMessage.get();
            oldMessage.setMessageText(newMessage.getMessageText());
            messageRepository.save(oldMessage);
            return 1;
        } else {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
}
