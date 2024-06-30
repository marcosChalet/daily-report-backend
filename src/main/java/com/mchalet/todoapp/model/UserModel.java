package com.mchalet.todoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class UserModel {
    @Id
    private String username;
    private String password;
    private String roles;
}
