package com.example.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "content", length = 1024)
    private String content;
    private Date createdAt;
    private UUID sender;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    private boolean isRead = false;
}
