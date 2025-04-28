package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "messages")
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByChatService", query = "SELECT m FROM Message m WHERE m.chatService.id = :chatServiceId"),
    @NamedQuery(name = "Message.findBySender", query = "SELECT m FROM Message m WHERE m.sender.id = :senderId"),
    @NamedQuery(name = "Message.findByTimestamp", query = "SELECT m FROM Message m WHERE m.timestamp = :timestamp"),
    @NamedQuery(name = "Message.findByChatServiceOrderByTimestampAsc", query = "SELECT m FROM Message m WHERE m.chatService.id = :chatServiceId ORDER BY m.timestamp ASC"),
    @NamedQuery(name = "Message.findByChatServiceOrderByTimestampDesc", query = "SELECT m FROM Message m WHERE m.chatService.id = :chatServiceId ORDER BY m.timestamp DESC")
})

public class Message implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "chat_service_id")
    private ChatService chatService;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private Date timestamp;

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
     * @return the chatService
     */
    public ChatService getChatService() {
        return chatService;
    }

    /**
     * @param chatService the chatService to set
     */
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * @return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
