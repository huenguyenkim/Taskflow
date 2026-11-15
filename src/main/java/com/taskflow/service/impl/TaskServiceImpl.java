package com.taskflow.service.impl;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import com.taskflow.exception.ForbiddenException;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.mapper.TaskMapper;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskMapper taskMapper;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        User user = getAuthenticatedUser();

        Task task = taskMapper.toEntity(
                request,
                user
        );

        Task savedTask =
                taskRepository.save(task);

        return taskMapper.toResponse(
                savedTask
        );
    }

    @Override
    public List<TaskResponse> getAllTasks() {

        User user = getAuthenticatedUser();

        return taskRepository.findByUser(user)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponse getTaskById(Long id) {

        User currentUser =
                getAuthenticatedUser();

        Task task =
                taskRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Task",
                                        "id",
                                        id
                                )
                        );

        if (!task.getUser().getId()
                .equals(currentUser.getId())) {

            throw new ForbiddenException(
                    "You do not have permission to access this task"
            );
        }

        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponse updateTask(
            Long id,
            TaskRequest request
    ) {

        User currentUser =
                getAuthenticatedUser();

        Task task =
                taskRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Task",
                                        "id",
                                        id
                                )
                        );

        if (!task.getUser().getId()
                .equals(currentUser.getId())) {

            throw new ForbiddenException(
                    "You do not have permission to update this task"
            );
        }

        taskMapper.updateEntity(
                task,
                request
        );

        Task updatedTask =
                taskRepository.save(task);

        return taskMapper.toResponse(
                updatedTask
        );
    }

    @Override
    public void deleteTask(Long id) {

        User currentUser =
                getAuthenticatedUser();

        Task task =
                taskRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Task",
                                        "id",
                                        id
                                )
                        );

        if (!task.getUser().getId()
                .equals(currentUser.getId())) {

            throw new ForbiddenException(
                    "You do not have permission to delete this task"
            );
        }

        taskRepository.delete(task);
    }

    /**
     * Lấy user đang đăng nhập từ JWT
     */
    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );
    }
}