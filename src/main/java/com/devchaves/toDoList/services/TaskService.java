package com.devchaves.toDoList.services;

import com.devchaves.toDoList.dtos.TaskDTO;
import com.devchaves.toDoList.entitys.TaskEntity;
import com.devchaves.toDoList.entitys.UsersEntity;
import com.devchaves.toDoList.entitys.wrappers.StatusWrapper;
import com.devchaves.toDoList.repositories.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    private UsersEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userService.getUserByEmail(authentication.getName());
    }

    public List<TaskEntity> getAllTasks(){
        UsersEntity currentUser = getCurrentUser();

        return taskRepository.findByUserId(currentUser);
    }

    public TaskEntity createTask(TaskDTO taskDTO){
        UsersEntity currentUser = getCurrentUser();

        TaskEntity task = new TaskEntity();

        task.setTitle(taskDTO.getTitle());
        task.setText(taskDTO.getText());
        task.setDescription(taskDTO.getDescription());

        if(taskDTO.getStatus() == null){
            task.setStatus(StatusWrapper.Status.PENDING);
        }else {
            task.setStatus(taskDTO.getStatus());
        }


        task.setPriority(taskDTO.getPriority());
        task.setDueDate(taskDTO.getDueDate());
        task.setUserId(currentUser);

        return taskRepository.save(task);

    }

    public TaskEntity updateTask(Long taskId, TaskDTO taskDTO) {
        UsersEntity currentUser = getCurrentUser();

        TaskEntity task = taskRepository.findByTaskIdAndUserId(taskId, currentUser)
                .orElseThrow(() -> new RuntimeException("Task not found or you don't have permission to update it"));

        // Update task fields
        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getText() != null) {
            task.setText(taskDTO.getText());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        }
        if (taskDTO.getPriority() != null) {
            task.setPriority(taskDTO.getPriority());
        }
        if (taskDTO.getDueDate() != null) {
            task.setDueDate(taskDTO.getDueDate());
        }

        return taskRepository.save(task);
    }



}
