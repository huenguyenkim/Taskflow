package com.taskflow.repository;

import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    Page<Task> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Page<Task> findByStatus(
            TaskStatus status,
            Pageable pageable
    );

    Page<Task> findByTitleContainingIgnoreCaseAndStatus(
            String keyword,
            TaskStatus status,
            Pageable pageable
    );

    long countByStatus(
            TaskStatus status
    );
}