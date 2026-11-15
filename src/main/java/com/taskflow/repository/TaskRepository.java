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
     * Lấy danh sách task theo user
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
     * Lấy task theo user và trạng thái
     */
    List<Task> findByUserAndStatus(
            User user,
            TaskStatus status
    );

    /**
     * Tìm kiếm theo tiêu đề
     */
    List<Task> findByTitleContainingIgnoreCase(
            String keyword
    );

    /**
     * Tìm kiếm task theo tiêu đề của user
     */
    List<Task> findByTitleContainingIgnoreCaseAndUser(
            String keyword,
            User user
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
     * Kiểm tra task tồn tại
     */
    boolean existsById(
            Long id
    );
}