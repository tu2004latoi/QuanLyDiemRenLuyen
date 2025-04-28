package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "news_feed")
public class NewsFeed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NewsFeed{" +
                "id=" + id +
                '}';
    }
}
