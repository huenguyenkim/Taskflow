
package com.taskflow.dto.response;

public record DashboardResponse(

        long totalTasks,

        long todoTasks,

        long inProgressTasks,

        long doneTasks

) {
}
