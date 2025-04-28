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
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    @OneToMany(mappedBy = "chatService")
    private Set<Message> messages;

    // Getters and setters
<<<<<<< HEAD
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

=======

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
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Set<Message> getMessages() {
        return messages;
    }

<<<<<<< HEAD
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatService{" +
                "id=" + id +
                '}';
    }
=======
    /**
     * @param messages the messages to set
     */
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
