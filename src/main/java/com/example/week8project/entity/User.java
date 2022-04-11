package com.example.week8project.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}
