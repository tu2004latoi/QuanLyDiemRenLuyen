package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "news_feed")
@NamedQueries({
    @NamedQuery(name = "NewsFeed.findAll", query = "SELECT n FROM NewsFeed n"),
    @NamedQuery(name = "NewsFeed.findById", query = "SELECT n FROM NewsFeed n WHERE n.id = :id")
})
public class NewsFeed implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

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
}
