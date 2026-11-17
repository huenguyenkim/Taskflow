package com.taskflow.service;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    TaskResponse getTaskById(Long id);

    List<TaskResponse> getAllTasks();

    TaskResponse updateTask(
            Long id,
            TaskRequest request
    );

    void deleteTask(Long id);

    // ===== NEW =====

    Page<TaskResponse> getTasks(
            int page,
            int size,
            String sortBy,
            String direction,
            String status,
            String keyword
    );
}