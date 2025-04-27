package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "chat_service")
public class ChatService implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy = "chatService")
    private Set<Message> messages;

    // Getters and setters
}
