package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "chat_service")
@NamedQueries({
    @NamedQuery(name = "ChatService.findAll", query = "SELECT c FROM ChatService c"),
    @NamedQuery(name = "ChatService.findById", query = "SELECT c FROM ChatService c WHERE c.id = :id")
})
public class ChatService implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy = "chatService")
    private Set<Message> messages;

    // Getters and setters

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the messages
     */
    public Set<Message> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
