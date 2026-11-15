package com.taskflow.entity;

/**
 * Trạng thái của công việc (Task)
 */
public enum TaskStatus {

    /**
     * Chưa bắt đầu
     */
    TODO,

    /**
     * Đang thực hiện
     */
    IN_PROGRESS,

    /**
     * Đã hoàn thành
     */
    DONE
}