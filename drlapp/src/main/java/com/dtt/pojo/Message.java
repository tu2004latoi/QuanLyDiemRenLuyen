package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

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

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
