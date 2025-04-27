package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "activities")
public class Activity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private java.util.Date startDate;

    @Column(name = "end_date")
    private java.util.Date endDate;

    @Column(name = "location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Staff organizer;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "current_participants")
    private Integer currentParticipants;

    @Column(name = "point_value")
    private Integer pointValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActivityStatus status;

    @OneToMany(mappedBy = "activity")
    private Set<TrainingPoint> trainingPoints;

    @OneToMany(mappedBy = "activity")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "activity")
    private Set<Like> likes;

    @OneToMany(mappedBy = "activity")
    private Set<Attendance> attendances;

    // Thêm trường image vào
    @Column(name = "image")
    private String image;

    // Getter and Setter methods for each field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Staff getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Staff organizer) {
        this.organizer = organizer;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(Integer currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public Integer getPointValue() {
        return pointValue;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public Set<TrainingPoint> getTrainingPoints() {
        return trainingPoints;
    }

    public void setTrainingPoints(Set<TrainingPoint> trainingPoints) {
        this.trainingPoints = trainingPoints;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    // Getter and Setter for image field
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Enum ActivityStatus
    public enum ActivityStatus {
        UPCOMING, ONGOING, COMPLETED, CANCELLED
    }
}
