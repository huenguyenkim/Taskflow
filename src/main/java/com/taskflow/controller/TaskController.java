package com.taskflow.controller;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.DashboardResponse;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.service.TaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

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

        return ResponseEntity.ok(

                taskService.getTasks(
                        page,
                        size,
                        sortBy,
                        direction,
                        status,
                        keyword
                )
        );
    }

    // Dashboard Statistics

    @GetMapping("/stats")
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                taskService.getDashboard()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                taskService.getTaskById(id)
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody TaskRequest request
    ) {

        return ResponseEntity.ok(
                taskService.createTask(request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequest request
    ) {

        return ResponseEntity.ok(
                taskService.updateTask(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id
    ) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}