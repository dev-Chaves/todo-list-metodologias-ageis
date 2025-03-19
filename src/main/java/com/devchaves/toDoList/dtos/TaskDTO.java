package com.devchaves.toDoList.dtos;

import com.devchaves.toDoList.entitys.wrappers.PriorityWrapper;
import com.devchaves.toDoList.entitys.wrappers.StatusWrapper;

import java.time.LocalDate;

public class TaskDTO {
    private String title;
    private String text;
    private String description;
    private StatusWrapper.Status status;
    private PriorityWrapper.Priority priority;
    private LocalDate dueDate;

    public TaskDTO() {
    }

    public TaskDTO(String title, String text, String description, StatusWrapper.Status status, PriorityWrapper.Priority priority, LocalDate dueDate) {
        this.title = title;
        this.text = text;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
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

    public StatusWrapper.Status getStatus() {
        return status;
    }

    public void setStatus(StatusWrapper.Status status) {
        this.status = status;
    }

    public PriorityWrapper.Priority getPriority() {
        return priority;
    }

    public void setPriority(PriorityWrapper.Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}