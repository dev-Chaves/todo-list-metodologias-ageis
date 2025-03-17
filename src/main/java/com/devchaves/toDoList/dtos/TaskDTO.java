package com.devchaves.toDoList.dtos;

import com.devchaves.toDoList.entitys.wrappers.PriorityWrapper;
import com.devchaves.toDoList.entitys.wrappers.StatusWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String title;
    private String text;
    private String description;
    private StatusWrapper.Status status;
    private PriorityWrapper.Priority priority;
    private LocalDate dueDate;
}