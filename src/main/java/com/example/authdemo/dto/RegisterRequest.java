package com.example.authdemo.dto;

import lombok.*;

@Getter @Setter
public class RegisterRequest {
    private String email;
    private String password;
}
