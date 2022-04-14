package com.example.stevenclassproject.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

@Entity(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 150)
    private String title;
    private String description;
    private String status;
    @CreationTimestamp
    @Column(name = "created_at")
    private Time createdAt;
    @Column(name = "updated_at")
    private Time updatedAt;
    @Column(name = "completed_at")
    private Time completedAt;
    @ManyToOne
    private Member member;

//    public Time getCreatedAt() {
//        return createdAt;
//    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Task() {
    }

    public Task(String title, String description, String status) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getCreateAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public Time getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Time updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Time getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Time completedAt) {
        this.completedAt = completedAt;
    }

}
