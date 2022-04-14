package com.example.stevenclassproject.repositories;

import com.example.stevenclassproject.models.Member;
import com.example.stevenclassproject.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
    Optional<Task> findByTitleAndMember(String title, Member member);
    Optional<Task> findById(Long taskId);
    Optional<Task> findByIdAndMember(Long taskId, Member member);
    List<Task> findAll();
    List<Task> findAllByStatus(String status);
    List<Task> findAllByStatusAndMember(String status, Member member);


}
