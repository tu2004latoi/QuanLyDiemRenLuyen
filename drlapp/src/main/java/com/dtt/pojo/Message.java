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

<<<<<<< HEAD
    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

=======
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
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public ChatService getChatService() {
        return chatService;
    }

<<<<<<< HEAD
=======
    /**
     * @param chatService the chatService to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

<<<<<<< HEAD
=======
    /**
     * @return the sender
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public User getSender() {
        return sender;
    }

<<<<<<< HEAD
=======
    /**
     * @param sender the sender to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setSender(User sender) {
        this.sender = sender;
    }

<<<<<<< HEAD
=======
    /**
     * @return the content
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public String getContent() {
        return content;
    }

<<<<<<< HEAD
=======
    /**
     * @param content the content to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setContent(String content) {
        this.content = content;
    }

<<<<<<< HEAD
=======
    /**
     * @return the timestamp
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Date getTimestamp() {
        return timestamp;
    }

<<<<<<< HEAD
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
=======
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
