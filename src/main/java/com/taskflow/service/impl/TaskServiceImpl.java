package com.taskflow.service.impl;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.DashboardResponse;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        task.setUser(user);

        Task savedTask =
                taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasks(
            int page,
            int size,
            String sortBy,
            String direction,
            String status,
            String keyword
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Task> tasks;

        TaskStatus taskStatus = null;

        if (status != null && !status.isBlank()) {

            taskStatus =
                    TaskStatus.valueOf(
                            status.toUpperCase()
                    );
        }

        if (keyword != null
                && !keyword.isBlank()
                && taskStatus != null) {

            tasks =
                    taskRepository
                            .findByTitleContainingIgnoreCaseAndStatus(
                                    keyword,
                                    taskStatus,
                                    pageable
                            );
        }

        else if (keyword != null
                && !keyword.isBlank()) {

            tasks =
                    taskRepository
                            .findByTitleContainingIgnoreCase(
                                    keyword,
                                    pageable
                            );
        }

        else if (taskStatus != null) {

            tasks =
                    taskRepository
                            .findByStatus(
                                    taskStatus,
                                    pageable
                            );
        }

        else {

            tasks =
                    taskRepository.findAll(
                            pageable
                    );
        }

        return tasks.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {

        Task task =
                taskRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Task not found with id: " + id
                                )
                        );

        return mapToResponse(task);
    }

    @Override
    public TaskResponse updateTask(
            Long id,
            TaskRequest request
    ) {

        Task task =
                taskRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Task not found with id: " + id
                                )
                        );

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        Task updatedTask =
                taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {

        Task task =
                taskRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Task not found with id: " + id
                                )
                        );

        taskRepository.delete(task);
    }

    // Dashboard Statistics

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {

        long totalTasks =
                taskRepository.count();

        long todoTasks =
                taskRepository.countByStatus(
                        TaskStatus.TODO
                );

        long inProgressTasks =
                taskRepository.countByStatus(
                        TaskStatus.IN_PROGRESS
                );

        long doneTasks =
                taskRepository.countByStatus(
                        TaskStatus.DONE
                );

        return new DashboardResponse(
                totalTasks,
                todoTasks,
                inProgressTasks,
                doneTasks
        );
    }

    private TaskResponse mapToResponse(Task task) {

        TaskResponse response =
                new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        if (task.getUser() != null) {

            response.setUserId(
                    task.getUser().getId()
            );

            response.setUsername(
                    task.getUser().getDisplayUsername()
            );
        }

        return response;
    }
}