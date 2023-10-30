package com.app.taskcontrol.service.impl;

import com.app.taskcontrol.entity.TaskEntity;
import com.app.taskcontrol.repository.TaskRepository;
import com.app.taskcontrol.service.TaskService;
import com.app.taskcontrol.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Page<TaskEntity> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public TaskEntity getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Override
    public TaskEntity createTask(TaskEntity task) {
        return taskRepository.save(task);
    }

    @Override
    public TaskEntity updateTask(Long id, TaskEntity task) {

        TaskEntity existingTask = getTaskById(id);

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        TaskEntity existingTask = getTaskById(id);
        taskRepository.delete(existingTask);
    }
}
