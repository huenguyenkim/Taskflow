package com.taskflow.service;

import com.taskflow.dto.request.TaskRequest;
import com.taskflow.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {

    /**
     * Tạo task mới
     *
     * @param request thông tin task
     * @return task đã tạo
     */
    TaskResponse createTask(TaskRequest request);

    /**
     * Lấy danh sách task của user đang đăng nhập
     *
     * @return danh sách task
     */
    List<TaskResponse> getAllTasks();

    /**
     * Lấy chi tiết task theo id
     *
     * @param id mã task
     * @return thông tin task
     */
    TaskResponse getTaskById(Long id);

    /**
     * Cập nhật task
     *
     * @param id mã task
     * @param request dữ liệu cập nhật
     * @return task sau khi cập nhật
     */
    TaskResponse updateTask(
            Long id,
            TaskRequest request
    );

    /**
     * Xóa task
     *
     * @param id mã task
     */
    void deleteTask(Long id);
}