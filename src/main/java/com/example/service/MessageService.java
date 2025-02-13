package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.SocialMediaAPIException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "Message text cannot be blank.");
        }

        if (message.getMessageText().length() > 255) {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "Message text cannot be over 255 characters.");
        }

        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "PostedBy must refer to an existing user.");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public int deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return 0;
        }
    }

    public int updateMessageText(Integer messageId, String messageText) {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "Message text cannot be blank or over 255 characters.");
        }

        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        } else {
            throw new SocialMediaAPIException(HttpStatus.BAD_REQUEST, "Message with id " + messageId + " not found.");
        }
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }


}
