package com.taskflow.repository;

import com.taskflow.entity.Task;
import com.taskflow.entity.TaskStatus;
import com.taskflow.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Lấy tất cả task của user
     */
    List<Task> findByUser(User user);

    /**
     * Phân trang task theo user
     */
    Page<Task> findByUser(
            User user,
            Pageable pageable
    );

    /**
     * Lấy task theo trạng thái
     */
    List<Task> findByStatus(
            TaskStatus status
    );

    /**
     * Phân trang task theo trạng thái
     */
    Page<Task> findByStatus(
            TaskStatus status,
            Pageable pageable
    );

    /**
     * Lấy task theo user và trạng thái
     */
    List<Task> findByUserAndStatus(
            User user,
            TaskStatus status
    );

    /**
     * Phân trang task theo user và trạng thái
     */
    Page<Task> findByUserAndStatus(
            User user,
            TaskStatus status,
            Pageable pageable
    );

    /**
     * Tìm kiếm theo title
     */
    List<Task> findByTitleContainingIgnoreCase(
            String keyword
    );

    /**
     * Tìm kiếm theo title có phân trang
     */
    Page<Task> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    /**
     * Tìm kiếm title theo user
     */
    List<Task> findByTitleContainingIgnoreCaseAndUser(
            String keyword,
            User user
    );

    /**
     * Tìm kiếm title theo user có phân trang
     */
    Page<Task> findByTitleContainingIgnoreCaseAndUser(
            String keyword,
            User user,
            Pageable pageable
    );

    /**
     * Tìm kiếm theo title và status
     */
    Page<Task> findByTitleContainingIgnoreCaseAndStatus(
            String keyword,
            TaskStatus status,
            Pageable pageable
    );

    /**
     * Tìm kiếm theo title + status + user
     */
    Page<Task> findByTitleContainingIgnoreCaseAndStatusAndUser(
            String keyword,
            TaskStatus status,
            User user,
            Pageable pageable
    );

    /**
     * Đếm task theo trạng thái
     */
    long countByStatus(
            TaskStatus status
    );

    /**
     * Đếm task của user
     */
    long countByUser(
            User user
    );

    /**
     * Đếm task của user theo trạng thái
     */
    long countByUserAndStatus(
            User user,
            TaskStatus status
    );

    /**
     * Kiểm tra task tồn tại
     */
    boolean existsById(
            Long id
    );
}