package com.project.task.mappers;

import com.project.task.domain.dto.TaskDto;
import com.project.task.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);

}
