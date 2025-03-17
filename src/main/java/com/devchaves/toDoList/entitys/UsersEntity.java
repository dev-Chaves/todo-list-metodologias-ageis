package com.devchaves.toDoList.entitys;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_users")
public class UsersEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

}
