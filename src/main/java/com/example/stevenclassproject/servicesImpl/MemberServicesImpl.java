package com.example.stevenclassproject.servicesImpl;

import com.example.stevenclassproject.dto.LoginDto;
import com.example.stevenclassproject.dto.MemberDto;
import com.example.stevenclassproject.dto.TaskDto;
import com.example.stevenclassproject.models.Member;
import com.example.stevenclassproject.models.Task;
import com.example.stevenclassproject.repositories.MemberRepository;
import com.example.stevenclassproject.repositories.TaskRepository;
import com.example.stevenclassproject.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class MemberServicesImpl implements MemberServices {

    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;

    @Autowired
    private MemberServicesImpl (MemberRepository memberRepository, TaskRepository taskRepository) {
        this.memberRepository = memberRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void createMember (MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        memberRepository.save(member);
    }

    @Override
    public Member login (LoginDto loginDto, HttpSession session) {
        Optional<Member> member = memberRepository.findByEmail(loginDto.getEmail());
        if (member.isPresent()) {
            Member presentMember = member.get();
            if (member.get().getPassword().equals(loginDto.getPassword())) {
                session.setAttribute("user", presentMember);
                session.setAttribute("id",presentMember.getId());
                session.setAttribute("userEmail",presentMember.getEmail());
                return presentMember;
            }
        }
        return null;
    }

    @Override
    public void createTask( TaskDto taskDto, HttpSession session) {
        Task newTask = new Task();
        newTask.setTitle(taskDto.getTitle());
        newTask.setDescription(taskDto.getDescription());
        newTask.setStatus("Pending");
        Time createdTime = new Time(System.currentTimeMillis());
        newTask.setCreatedAt(createdTime);
        Time updatedTime = new Time(System.currentTimeMillis());
        newTask.setCreatedAt(updatedTime);
        newTask.setMember((Member)session.getAttribute("userEmail"));
        taskRepository.save(newTask);
        Optional<Member> member = memberRepository.findByEmail(newTask.getMember().getEmail());
        member.get().getListOfTasks().add(newTask);
    }

    @Override
    public void updateTask(Long taskId,TaskDto taskDto,HttpSession session) {
        Member member = (Member) session.getAttribute("user");
        Optional<Task> task = taskRepository.findByIdAndMember(taskId,member);
//        Task task2= taskRepository.getOne(taskId);
        task.orElseThrow().setTitle(taskDto.getTitle());
        task.get().setDescription(taskDto.getDescription());
        task.get().setStatus(taskDto.getStatus());
        task.get().setUpdatedAt(new Time(System.currentTimeMillis()));
        taskRepository.save(task.orElseThrow());
        Optional<Member> memberToUpdate = memberRepository.findById(member.getId());
//        Optional<Member> member = memberRepository.findByEmail(task.get().getMember().getEmail());
        Task taskToUpdate = new Task();
        int index = 0;
        for (int i = 0; i < memberToUpdate.orElseThrow().getListOfTasks().size(); i++) {
            if (memberToUpdate.get().getListOfTasks().get(i).getTitle().equals(task.get().getTitle())) {
                taskToUpdate = memberToUpdate.get().getListOfTasks().get(i);
                index = i;
                break;
            }
        }
        taskToUpdate.setTitle(task.get().getTitle());
        taskToUpdate.setDescription(task.get().getDescription());
        taskToUpdate.setUpdatedAt(task.get().getUpdatedAt());
        memberToUpdate.get().getListOfTasks().remove(index);
        memberToUpdate.get().getListOfTasks().add(index,taskToUpdate);
    }

    @Override
    public void deleteTask(Long taskId,HttpSession session) {
        Optional<Task> task = taskRepository.findById(taskId);
//        Optional<Member> member = memberRepository.findByEmail(task.orElseThrow().getMember().getEmail());
        Member member = (Member) session.getAttribute("user");
        //Todo ====> Add custom exception if this does not work
        Optional<Member> memberToDeleteTaskFrom = memberRepository.findById(member.getId());
        memberToDeleteTaskFrom.orElseThrow().getListOfTasks().remove(task.orElseThrow());
        taskRepository.delete(task.orElseThrow());
//        List<Task> listOfTasks= taskRepository.findAll();
    }

    @Override
    public Task viewTask(TaskDto taskDto,HttpSession session) {
//        Task newTask = new Task();
//        newTask.setTitle(taskDto.getTitle());
        Member member = (Member) session.getAttribute("user");
        Optional<Task> selectedTask = taskRepository.findByTitleAndMember(taskDto.getTitle(),member);
        return selectedTask.orElseThrow();
    }

    @Override
    public List<Task> viewAllMemberTasks(HttpSession session) {
//        Optional<List<Task>> listOfTasks = Optional.ofNullable(taskRepository.findAll());
//        return listOfTasks.orElseThrow();
        Member member = (Member) session.getAttribute("user");
        return  memberRepository.findById(member.getId()).orElseThrow().getListOfTasks();
    }

    @Override
    public List<Task> viewAllMemberPendingTasks(String status, HttpSession session) {
//        Optional<List<Task>> listOfTasksByStatus = Optional.ofNullable(taskRepository.findAllByStatus(status));
//        return listOfTasksByStatus.orElseThrow();
        Member member = (Member) session.getAttribute("user");
        Optional<Member> memberToViewPendingTasksFrom = memberRepository.findById(member.getId());
        return taskRepository.findAllByStatusAndMember(status,memberToViewPendingTasksFrom.orElseThrow());
    }

    @Override
    public List<Task> viewAllMemberTasksInProgress(String status, HttpSession session) {
        Member member = (Member) session.getAttribute("user");
        Optional<Member> memberToViewTasksInProgressFrom = memberRepository.findById(member.getId());
        return taskRepository.findAllByStatusAndMember(status,memberToViewTasksInProgressFrom.orElseThrow());
    }

    @Override
    public List<Task> viewAllMemberDoneTasks(String status, HttpSession session) {
        Member member = (Member) session.getAttribute("user");
        Optional<Member> memberToViewAllDoneTasksFrom = memberRepository.findById(member.getId());
        return taskRepository.findAllByStatusAndMember(status,memberToViewAllDoneTasksFrom.orElseThrow());
    }


}
