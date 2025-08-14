package com.project.task.services.impl;

import com.project.task.domain.entities.Task;
import com.project.task.domain.entities.TaskList;
import com.project.task.domain.entities.TaskPriority;
import com.project.task.domain.entities.TaskStatus;
import com.project.task.repositories.TaskListRepository;
import com.project.task.repositories.TaskRepository;
import com.project.task.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public List<Task> listTask(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(task.getId() != null){ // generate Id in Entities
            throw new IllegalArgumentException("Task Already has an ID");
        }
        if(task.getTitle() == null){
            throw new IllegalArgumentException("Task Must have a title.");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid Task List ID Provider"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskID) {
        return taskRepository.findByTaskListIdAndId(taskListId , taskID);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId() == null ){
            throw new IllegalArgumentException("Task Must Have an ID");
        }
        if( task.getPriority() == null){
            throw new IllegalArgumentException("Task Must have Valid priority");
        }
        if(task.getStatus() == null){
            throw new IllegalArgumentException("Task Must have a Valid Status");
        }
        if(!Objects.equals(taskId , task.getId())){
            throw new IllegalArgumentException("Task Id's Do not Match");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId ,taskId)
                .orElseThrow(()->new IllegalArgumentException("Task Does not Exist"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListID, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListID , taskId);
    }
}
