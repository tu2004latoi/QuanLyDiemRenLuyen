package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "news_feed")
public class NewsFeed implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    // Getters and setters
}
