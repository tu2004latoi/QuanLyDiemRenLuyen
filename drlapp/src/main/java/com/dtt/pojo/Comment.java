package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comments")
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id"),
    @NamedQuery(name = "Comment.findByStudent", query = "SELECT c FROM Comment c WHERE c.student = :student"),
    @NamedQuery(name = "Comment.findByActivity", query = "SELECT c FROM Comment c WHERE c.activity = :activity"),
    @NamedQuery(name = "Comment.findByContent", query = "SELECT c FROM Comment c WHERE c.content = :content"),
    @NamedQuery(name = "Comment.findByCreatedAt", query = "SELECT c FROM Comment c WHERE c.createdAt = :createdAt")
})
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date createdAt;

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
     * @return the student
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Student getStudent() {
        return student;
    }

<<<<<<< HEAD
=======
    /**
     * @param student the student to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setStudent(Student student) {
        this.student = student;
    }

<<<<<<< HEAD
=======
    /**
     * @return the activity
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Activity getActivity() {
        return activity;
    }

<<<<<<< HEAD
=======
    /**
     * @param activity the activity to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setActivity(Activity activity) {
        this.activity = activity;
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
     * @return the createdAt
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Date getCreatedAt() {
        return createdAt;
    }

<<<<<<< HEAD
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", student=" + student +
                ", activity=" + activity +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
=======
    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
