package com.project.task.services.impl;

import com.project.task.domain.entities.TaskList;
import com.project.task.repositories.TaskListRepository;
import com.project.task.services.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Transactional
    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(null != taskList.getId()){
            throw new IllegalArgumentException("TaskList Already has an ID !");
        }
        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task List title must be present !");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if(taskList.getId() == null){
            throw new IllegalArgumentException("Task List must have an ID");
        }

        if(!Objects.equals(taskList.getId() , taskListId)){
            throw new IllegalArgumentException("Attempting to change task list ID , this is not permitted");
        }
        TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow(()->new IllegalArgumentException("Task List not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);

    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }
}
