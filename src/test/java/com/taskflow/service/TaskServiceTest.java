
package com.taskflow.service;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.DashboardResponse;
import com.taskflow.dto.response.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.impl.TaskServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    // Xóa SecurityContext sau mỗi test
    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    // =====================================================
    // 1. GET TASK BY ID
    // =====================================================

    // Test 1: Lấy Task thành công
    @Test
    void getTaskById_shouldReturnTask_whenTaskExists() {

        // Arrange
        Task task = createTask(1L, "Learn Java", TaskStatus.TODO);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        // Act
        TaskResponse result = taskService.getTaskById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Learn Java", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());

        verify(taskRepository).findById(1L);
    }

    // Test 2: Không tìm thấy Task
    @Test
    void getTaskById_shouldThrowException_whenTaskNotFound() {

        // Arrange
        when(taskRepository.findById(2L))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> taskService.getTaskById(2L)
                );

        assertEquals(
                "Task not found with id: 2",
                exception.getMessage()
        );

        verify(taskRepository).findById(2L);
    }

    // =====================================================
    // 2. CREATE TASK
    // =====================================================

    // Test 3: Tạo Task thành công
    @Test
    void createTask_shouldReturnTask_whenUserExists() {

        // Arrange
        setUpAuthentication("user@gmail.com");

        User user = createUser(1L);

        when(userRepository.findByEmail("user@gmail.com"))
                .thenReturn(Optional.of(user));

        TaskRequest request = createTaskRequest(
                "Learn Java",
                "Study Spring Boot",
                TaskStatus.TODO
        );

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> {

                    Task task = invocation.getArgument(0);

                    task.setId(100L);

                    return task;
                });

        // Act
        TaskResponse result = taskService.createTask(request);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals("Learn Java", result.getTitle());
        assertEquals("Study Spring Boot", result.getDescription());
        assertEquals(TaskStatus.TODO, result.getStatus());

        assertEquals(1L, result.getUserId());

        verify(userRepository).findByEmail("user@gmail.com");

        ArgumentCaptor<Task> captor =
                ArgumentCaptor.forClass(Task.class);

        verify(taskRepository).save(captor.capture());

        Task savedTask = captor.getValue();

        assertEquals("Learn Java", savedTask.getTitle());
        assertEquals("Study Spring Boot", savedTask.getDescription());
        assertEquals(TaskStatus.TODO, savedTask.getStatus());
        assertEquals(user, savedTask.getUser());
    }

    // Test 4: Không tìm thấy User khi tạo Task
    @Test
    void createTask_shouldThrowException_whenUserNotFound() {

        // Arrange
        setUpAuthentication("user@gmail.com");

        when(userRepository.findByEmail("user@gmail.com"))
                .thenReturn(Optional.empty());

        TaskRequest request = createTaskRequest(
                "Learn Java",
                "Study Spring Boot",
                TaskStatus.TODO
        );

        // Act + Assert
        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> taskService.createTask(request)
                );

        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findByEmail("user@gmail.com");

        verify(taskRepository, never())
                .save(any(Task.class));
    }

    // =====================================================
    // 3. UPDATE TASK
    // =====================================================

    // Test 5: Cập nhật Task thành công
    @Test
    void updateTask_shouldReturnUpdatedTask_whenTaskExists() {

        // Arrange
        Task task = createTask(
                1L,
                "Old title",
                TaskStatus.TODO
        );

        TaskRequest request = createTaskRequest(
                "New title",
                "New description",
                TaskStatus.IN_PROGRESS
        );

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskResponse result = taskService.updateTask(1L, request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New title", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    // Test 6: Cập nhật Task không tồn tại
    @Test
    void updateTask_shouldThrowException_whenTaskNotFound() {

        // Arrange
        TaskRequest request = createTaskRequest(
                "New title",
                "New description",
                TaskStatus.TODO
        );

        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.updateTask(1L, request)
        );

        verify(taskRepository).findById(1L);

        verify(taskRepository, never())
                .save(any(Task.class));
    }

    // =====================================================
    // 4. DELETE TASK
    // =====================================================

    // Test 7: Xóa Task thành công
    @Test
    void deleteTask_shouldDeleteTask_whenTaskExists() {

        // Arrange
        Task task = createTask(
                1L,
                "Learn Java",
                TaskStatus.TODO
        );

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(task);
    }

    // Test 8: Xóa Task không tồn tại
    @Test
    void deleteTask_shouldThrowException_whenTaskNotFound() {

        // Arrange
        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.deleteTask(1L)
        );

        verify(taskRepository).findById(1L);

        verify(taskRepository, never())
                .delete(any(Task.class));
    }

    // =====================================================
    // 5. GET ALL TASKS
    // =====================================================

    // Test 9: Lấy danh sách Task thành công
    @Test
    void getAllTasks_shouldReturnTaskList() {

        // Arrange
        Task task1 = createTask(
                1L,
                "Learn Java",
                TaskStatus.TODO
        );

        Task task2 = createTask(
                2L,
                "Learn Spring Boot",
                TaskStatus.IN_PROGRESS
        );

        when(taskRepository.findAll())
                .thenReturn(Arrays.asList(task1, task2));

        // Act
        List<TaskResponse> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Learn Java", result.get(0).getTitle());
        assertEquals("Learn Spring Boot", result.get(1).getTitle());

        verify(taskRepository).findAll();
    }

    // Test 10: Không có Task
    @Test
    void getAllTasks_shouldReturnEmptyList_whenNoTasksExist() {

        // Arrange
        when(taskRepository.findAll())
                .thenReturn(Collections.emptyList());

        // Act
        List<TaskResponse> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(taskRepository).findAll();
    }

    // =====================================================
    // 6. GET TASKS - PAGINATION, SEARCH AND FILTER
    // =====================================================

    // Test 11: Lấy tất cả Task có phân trang
    @Test
    void getTasks_shouldReturnPage_whenNoFilterApplied() {

        // Arrange
        Task task = createTask(
                1L,
                "Learn Java",
                TaskStatus.TODO
        );

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("createdAt").ascending()
        );

        Page<Task> taskPage = new PageImpl<>(
                Collections.singletonList(task),
                pageable,
                1
        );

        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(taskPage);

        // Act
        Page<TaskResponse> result = taskService.getTasks(
                0,
                5,
                "createdAt",
                "asc",
                null,
                null
        );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Learn Java", result.getContent().get(0).getTitle());

        verify(taskRepository).findAll(any(Pageable.class));
    }

    // Test 12: Tìm kiếm theo Keyword
    @Test
    void getTasks_shouldSearchByKeyword_whenKeywordProvided() {

        // Arrange
        Task task = createTask(
                1L,
                "Learn Java",
                TaskStatus.TODO
        );

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("createdAt").ascending()
        );

        Page<Task> taskPage = new PageImpl<>(
                Collections.singletonList(task),
                pageable,
                1
        );

        when(taskRepository.findByTitleContainingIgnoreCase(
                eq("Java"),
                any(Pageable.class)
        )).thenReturn(taskPage);

        // Act
        Page<TaskResponse> result = taskService.getTasks(
                0,
                5,
                "createdAt",
                "asc",
                null,
                "Java"
        );

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Learn Java", result.getContent().get(0).getTitle());

        verify(taskRepository).findByTitleContainingIgnoreCase(
                eq("Java"),
                any(Pageable.class)
        );
    }

    // Test 13: Lọc theo Status
    @Test
    void getTasks_shouldFilterByStatus_whenStatusProvided() {

        // Arrange
        Task task = createTask(
                1L,
                "Learn Java",
                TaskStatus.TODO
        );

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("createdAt").ascending()
        );

        Page<Task> taskPage = new PageImpl<>(
                Collections.singletonList(task),
                pageable,
                1
        );

        when(taskRepository.findByStatus(
                eq(TaskStatus.TODO),
                any(Pageable.class)
        )).thenReturn(taskPage);

        // Act
        Page<TaskResponse> result = taskService.getTasks(
                0,
                5,
                "createdAt",
                "asc",
                "TODO",
                null
        );

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(TaskStatus.TODO, result.getContent().get(0).getStatus());

        verify(taskRepository).findByStatus(
                eq(TaskStatus.TODO),
                any(Pageable.class)
        );
    }

    // Test 14: Tìm kiếm Keyword kết hợp Status
    @Test
    void getTasks_shouldSearchByKeywordAndStatus() {

        // Arrange
        Task task = createTask(
                1L,
                "Learn Java",
                TaskStatus.IN_PROGRESS
        );

        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by("createdAt").descending()
        );

        Page<Task> taskPage = new PageImpl<>(
                Collections.singletonList(task),
                pageable,
                1
        );

        when(taskRepository.findByTitleContainingIgnoreCaseAndStatus(
                eq("Java"),
                eq(TaskStatus.IN_PROGRESS),
                any(Pageable.class)
        )).thenReturn(taskPage);

        // Act
        Page<TaskResponse> result = taskService.getTasks(
                0,
                5,
                "createdAt",
                "desc",
                "IN_PROGRESS",
                "Java"
        );

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(
                TaskStatus.IN_PROGRESS,
                result.getContent().get(0).getStatus()
        );

        verify(taskRepository)
                .findByTitleContainingIgnoreCaseAndStatus(
                        eq("Java"),
                        eq(TaskStatus.IN_PROGRESS),
                        any(Pageable.class)
                );
    }

    // =====================================================
    // 7. DASHBOARD STATISTICS
    // =====================================================

    // Test 15: Lấy thống kê Dashboard
    @Test
    void getDashboard_shouldReturnCorrectStatistics() {

        // Arrange
        when(taskRepository.count())
                .thenReturn(10L);

        when(taskRepository.countByStatus(TaskStatus.TODO))
                .thenReturn(3L);

        when(taskRepository.countByStatus(TaskStatus.IN_PROGRESS))
                .thenReturn(4L);

        when(taskRepository.countByStatus(TaskStatus.DONE))
                .thenReturn(3L);

        // Act
        DashboardResponse result = taskService.getDashboard();

        // Assert
        assertNotNull(result);

        assertEquals(10, result.totalTasks());
        assertEquals(3, result.todoTasks());
        assertEquals(4, result.inProgressTasks());
        assertEquals(3, result.doneTasks());

        // Verify
        verify(taskRepository).count();
        verify(taskRepository).countByStatus(TaskStatus.TODO);
        verify(taskRepository).countByStatus(TaskStatus.IN_PROGRESS);
        verify(taskRepository).countByStatus(TaskStatus.DONE);
    }

    // =====================================================
    // 8. HELPER METHODS
    // =====================================================

    // Tạo Task giả lập
    private Task createTask(
            Long id,
            String title,
            TaskStatus status
    ) {

        Task task = new Task();

        task.setId(id);
        task.setTitle(title);
        task.setDescription("Test description");
        task.setStatus(status);

        return task;
    }

    // Tạo User giả lập
    private User createUser(Long id) {

        User user = new User();

        user.setId(id);

        return user;
    }

    // Tạo TaskRequest giả lập
    private TaskRequest createTaskRequest(
            String title,
            String description,
            TaskStatus status
    ) {

        TaskRequest request = new TaskRequest();

        request.setTitle(title);
        request.setDescription(description);
        request.setStatus(status);

        return request;
    }

    // Thiết lập Authentication giả lập
    private void setUpAuthentication(String email) {

        Authentication authentication =
                mock(Authentication.class);

        when(authentication.getName())
                .thenReturn(email);

        SecurityContext securityContext =
                SecurityContextHolder.createEmptyContext();

        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
    }
}
