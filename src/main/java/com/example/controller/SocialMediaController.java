package com.example.controller;

import com.example.entity.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> postRegister(@RequestBody Account body){
        if(body.getPassword().length() >= 4 && (body.getUsername() != null || body.getUsername() != "" )) {
            if(accountService.getAccountByUsername(body.getUsername())) {
                return ResponseEntity.status(409).body(null);
            }
            return ResponseEntity.status(200).body(accountService.registerAccount(body));
        }
        return ResponseEntity.status(400).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account body){
        Account check = accountService.getAccountByUsernameAndPassword(body.getUsername(), body.getPassword());
        if(check != null) {
            return ResponseEntity.status(200).body(check);
        }
        return ResponseEntity.status(401).body(null);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message body) throws NoSuchElementException{
        if(body.getMessageText().length() < 255 && (body.getMessageText() != null && body.getMessageText() != "" )) {
            try {
                accountService.existUser(body.getPostedBy());
                return ResponseEntity.status(200).body(messageService.addMessage(body));
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(400).body(null);
            }
        }
        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return new ResponseEntity<>(allMessages, HttpStatus.OK);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageByPostId(@PathVariable Integer message_id) throws NoSuchElementException{
        try {
            Message messagesById = messageService.getMessageById(message_id);
            return new ResponseEntity<>(messagesById, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer account_id){
        List<Message> allMessages = messageService.getUserMessages(account_id);
        return ResponseEntity.status(200).body(allMessages);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByPostId(@PathVariable int message_id) throws NoSuchElementException{
        try {
            messageService.getMessageById(message_id);
            messageService.deleteMessage(message_id);
            return new ResponseEntity<>(1, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageByPostId(@PathVariable int message_id, @RequestBody Message updatedMessage) throws NoSuchElementException{
        System.out.println(message_id);
        if(updatedMessage.getMessageText().length() < 255 && (updatedMessage.getMessageText() != null && updatedMessage.getMessageText() != "" )) {
            try {
                messageService.getMessageById(message_id);
                int row = messageService.updateMessage(message_id, updatedMessage);
                return ResponseEntity.status(200).body(row);
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(400).body(null);
            }
        }
        return ResponseEntity.status(400).body(null);
    }
}
