package com.project.task.mappers;

import com.project.task.domain.dto.TaskListDto;
import com.project.task.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
