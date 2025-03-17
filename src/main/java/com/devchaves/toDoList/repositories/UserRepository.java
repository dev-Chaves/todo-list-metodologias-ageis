package com.devchaves.toDoList.repositories;

import com.devchaves.toDoList.entitys.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long > {
    Optional<UsersEntity> findByEmail(String email);
}
