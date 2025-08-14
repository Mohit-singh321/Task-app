package com.project.task.services;

import com.project.task.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTask(UUID taskListId);

    Task createTask(UUID taskListId,Task task);

    Optional<Task> getTask(UUID taskListId , UUID taskID);

    Task updateTask(UUID taskListId , UUID taskId , Task task);

    void deleteTask(UUID taskListID , UUID taskId);

}
