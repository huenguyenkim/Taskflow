package com.taskflow.mapper;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    /**
     * Convert TaskRequest -> Task Entity
     */
    public Task toEntity(
            TaskRequest request,
            User user
    ) {

        if (request == null) {
            return null;
        }

        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(
                        request.getStatus() != null
                                ? request.getStatus()
                                : TaskStatus.TODO
                )
                .user(user)
                .build();
    }

    /**
     * Convert Task Entity -> TaskResponse
     */
    public TaskResponse toResponse(
            Task task
    ) {

        if (task == null) {
            return null;
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .userId(
                        task.getUser() != null
                                ? task.getUser().getId()
                                : null
                )
                .username(
                        task.getUser() != null
                                ? task.getUser().getUsername()
                                : null
                )
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    /**
     * Update Task Entity từ TaskRequest
     */
    public void updateEntity(
            Task task,
            TaskRequest request
    ) {

        if (task == null || request == null) {
            return;
        }

        task.setTitle(request.getTitle());

        task.setDescription(
                request.getDescription()
        );

        if (request.getStatus() != null) {
            task.setStatus(
                    request.getStatus()
            );
        }
    }
}