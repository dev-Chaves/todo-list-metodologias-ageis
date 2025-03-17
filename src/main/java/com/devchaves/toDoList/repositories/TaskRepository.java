package com.devchaves.toDoList.repositories;

import com.devchaves.toDoList.entitys.TaskEntity;
import com.devchaves.toDoList.entitys.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByUserId(UsersEntity userId);

    Optional<TaskEntity> findByTaskByIdAndUserId(Long taskId, UsersEntity userId);

}
