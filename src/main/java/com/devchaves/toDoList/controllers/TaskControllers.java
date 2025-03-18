package com.devchaves.toDoList.controllers;

import com.devchaves.toDoList.dtos.TaskDTO;
import com.devchaves.toDoList.entitys.TaskEntity;
import com.devchaves.toDoList.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskControllers {

    private final TaskService taskService;

    public TaskControllers(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskEntity>> getTasks(){
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskEntity> addTask(@Valid @RequestBody TaskDTO taskDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDTO));
    }

    @PutMapping("/tasks/${id}")
    public ResponseEntity<TaskEntity> updateTask (@PathVariable("id") Long taskId, @RequestBody TaskDTO taskDTO){
        return ResponseEntity.ok().body(taskService.updateTask(taskId, taskDTO));
    }

    @DeleteMapping("/tasks/${id}")
    public ResponseEntity<TaskEntity> deleteTask (@PathVariable("id") Long taskId){

        deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }

}
