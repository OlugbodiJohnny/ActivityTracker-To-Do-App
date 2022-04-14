package com.example.stevenclassproject.services;

import com.example.stevenclassproject.dto.LoginDto;
import com.example.stevenclassproject.dto.MemberDto;
import com.example.stevenclassproject.dto.TaskDto;
import com.example.stevenclassproject.models.Member;
import com.example.stevenclassproject.models.Task;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface MemberServices {
    void createMember (MemberDto memberDto);
    Member login(LoginDto loginDto, HttpSession session);
    void createTask(TaskDto taskDto, HttpSession session);
    void updateTask(Long taskId,TaskDto taskDto,HttpSession session);
    void deleteTask(Long taskId, HttpSession session);
    Task viewTask(TaskDto taskDto,HttpSession session);
    List<Task> viewAllMemberTasks(HttpSession session);
    List<Task> viewAllMemberPendingTasks(String status, HttpSession session);
    List<Task> viewAllMemberTasksInProgress (String status, HttpSession session);
    List<Task> viewAllMemberDoneTasks(String status, HttpSession session);

}
