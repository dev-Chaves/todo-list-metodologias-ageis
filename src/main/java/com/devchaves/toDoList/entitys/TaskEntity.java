package com.devchaves.toDoList.entitys;

import com.devchaves.toDoList.entitys.wrappers.PriorityWrapper;
import com.devchaves.toDoList.entitys.wrappers.StatusWrapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
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

}



