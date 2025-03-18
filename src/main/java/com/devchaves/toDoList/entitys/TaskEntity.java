package com.devchaves.toDoList.entitys;

import com.devchaves.toDoList.entitys.wrappers.PriorityWrapper;
import com.devchaves.toDoList.entitys.wrappers.StatusWrapper;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tasks")
public class TaskEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    private String description;

    @Column(nullable = false)
    private String status;

    private String priority;

    private LocalDate dueDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity userId;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }

    public void setStatus(StatusWrapper.Status status) {
        this.status = status.getLabel();
    }

    public StatusWrapper.Status getStatus() {
        return StatusWrapper.Status.fromString(this.status);
    }

    public void setPriority(PriorityWrapper.Priority priority) {
        this.priority = priority.getLabel();
    }

    public PriorityWrapper.Priority getPriority() {
        return PriorityWrapper.Priority.fromString(this.priority);
    }

    public TaskEntity() {

    }

    public TaskEntity(Long taskId, String title, String text, String description, String status, String priority, LocalDate dueDate, LocalDateTime createdAt, LocalDateTime updateAt, UsersEntity userId) {
        this.taskId = taskId;
        this.title = title;
        this.text = text;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }
}



