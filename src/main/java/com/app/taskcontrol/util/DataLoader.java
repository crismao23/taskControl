package com.app.taskcontrol.util;

import com.app.taskcontrol.entity.TaskEntity;
import com.app.taskcontrol.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final TaskRepository taskRepository;

    @Autowired
    public DataLoader(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LocalDateTime currentDate = LocalDateTime.now();

        for (int i = 0; i < 20; i++) {
            TaskEntity task = new TaskEntity();
            task.setTitle("Task number #" + (i + 1));
            task.setDescription("Description of task number " + (i + 1));
            task.setDueDate(currentDate.plusDays(i + 1));
            task.setStatus("Pending task");
            taskRepository.save(task);
        }
    }
}
