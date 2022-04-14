package com.example.stevenclassproject.dto;

import com.example.stevenclassproject.models.Member;
import lombok.Getter;

@Getter
public class TaskDto {
    private String title;
    private String description;
    private String status;
    private String email;
    private Member member;
}
