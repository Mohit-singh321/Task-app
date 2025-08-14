package com.project.task.controllers;

import com.project.task.domain.dto.TaskListDto;
import com.project.task.domain.entities.TaskList;
import com.project.task.mappers.TaskListMapper;
import com.project.task.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {
    @Autowired
    private TaskListService taskListService;

    @Autowired
    private TaskListMapper taskListMapper;

    @GetMapping
    public List<TaskListDto> listTaskLists(){
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto){
         TaskList createdTaskList = taskListService.createTaskList(
                 taskListMapper.fromDto(taskListDto)
         );
         return taskListMapper.toDto(createdTaskList);
    }
    @GetMapping("/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID id){
        return taskListService.getTaskList(id).map(taskListMapper::toDto);
    }


    @PutMapping(path= "/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID taskListId,
                                      @RequestBody TaskListDto taskListDto){
        TaskList UpdatedTaskList = taskListService.updateTaskList(taskListId , taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(UpdatedTaskList);
    }

    @DeleteMapping(path="/{taskListId}")
    public void deleteTaskList(@PathVariable("taskListId") UUID id){
        taskListService.deleteTaskList(id);
    }


}
