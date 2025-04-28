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
<<<<<<< HEAD

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
=======

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
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
