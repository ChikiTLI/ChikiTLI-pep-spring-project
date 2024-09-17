package com.example.repository;

import com.example.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    // @Query("FROM message m WHERE m.messageId = :id")
    // Message findMessageById(@Param("id") Integer id);
    List<Message> findMessagesByPostedBy(Integer postedBy);
}