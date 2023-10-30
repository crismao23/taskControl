package com.app.taskcontrol.service;

import com.app.taskcontrol.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TaskService {

    List<TaskEntity> getAllTasks();

    Page<TaskEntity> getAllTasks(Pageable pageable);

    TaskEntity getTaskById(Long id);

    TaskEntity createTask(TaskEntity task);

    TaskEntity updateTask(Long id, TaskEntity task);

    void deleteTask(Long id);
}
