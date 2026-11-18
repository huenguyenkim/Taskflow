package com.taskflow.service;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.DashboardResponse;
import com.taskflow.dto.response.TaskResponse;

import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    List<TaskResponse> getAllTasks();

    Page<TaskResponse> getTasks(
            int page,
            int size,
            String sortBy,
            String direction,
            String status,
            String keyword
    );

    TaskResponse getTaskById(
            Long id
    );

    TaskResponse createTask(
            TaskRequest request
    );

    TaskResponse updateTask(
            Long id,
            TaskRequest request
    );

    void deleteTask(
            Long id
    );

    DashboardResponse getDashboard();
}