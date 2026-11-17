package com.taskflow.controller;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Tạo task mới
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request
    ) {

        TaskResponse response =
                taskService.createTask(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Pagination + Search + Sort + Filter
     */
    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getTasks(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String direction,

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            String keyword
    ) {

        Page<TaskResponse> tasks =
                taskService.getTasks(
                        page,
                        size,
                        sortBy,
                        direction,
                        status,
                        keyword
                );

        return ResponseEntity.ok(tasks);
    }

    /**
     * Lấy chi tiết task theo id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id
    ) {

        TaskResponse task =
                taskService.getTaskById(id);

        return ResponseEntity.ok(task);
    }

    /**
     * Cập nhật task
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {

        TaskResponse response =
                taskService.updateTask(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

    /**
     * Xóa task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id
    ) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}