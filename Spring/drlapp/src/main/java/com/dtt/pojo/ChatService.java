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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatService{" +
                "id=" + id +
                '}';
    }
}
