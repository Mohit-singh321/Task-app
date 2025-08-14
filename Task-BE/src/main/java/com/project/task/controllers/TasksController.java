package com.project.task.controllers;

import com.project.task.domain.dto.TaskDto;
import com.project.task.domain.entities.Task;
import com.project.task.mappers.TaskMapper;
import com.project.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists/{task-list-id}/tasks")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task-list-id") UUID taskListId){
        return taskService.listTask(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(
            @PathVariable("task-list-id") UUID taskListId,
            @RequestBody TaskDto taskDto
            ){
        Task createdTask = taskService.createTask(
                taskListId ,
                taskMapper.fromDto(taskDto)
        );

        return taskMapper.toDto(createdTask);


    }

    @GetMapping("/{taskId}")
    public Optional<TaskDto> getTaskById(
            @PathVariable("task-list-id") UUID taskListId,
            @PathVariable("taskId") UUID taskId){
        return taskService.getTask(taskListId , taskId).map(taskMapper::toDto);
    }

    @PutMapping("/{taskId}")
    public TaskDto updateTask(
            @PathVariable("task-list-id") UUID taskListId,
            @PathVariable("taskId") UUID taskID,
            @RequestBody TaskDto taskDto
    ){
            Task updatedTask = taskService.updateTask(taskListId , taskID , taskMapper.fromDto(taskDto));
            return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path="/{taskId}")
    public void deleteTask(
            @PathVariable("task-list-id") UUID taskListId,
            @PathVariable("taskId") UUID taskID
    ){
        taskService.deleteTask(taskListId , taskID);
    }


}
