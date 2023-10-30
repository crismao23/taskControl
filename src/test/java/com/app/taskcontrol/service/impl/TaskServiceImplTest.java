package com.app.taskcontrol.service.impl;

import com.app.taskcontrol.entity.TaskEntity;
import com.app.taskcontrol.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private List<TaskEntity> taskList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        LocalDateTime currentDate = LocalDateTime.now();
        for (Long i = 0L; i < 10L; i++) {
            TaskEntity task = new TaskEntity();
            task.setId(i + 1L);
            task.setTitle("Tarea " + (i + 1));
            task.setDescription("Descripción de la tarea " + (i + 1));
            task.setDueDate(currentDate.plusDays(i + 1));
            task.setStatus("Pendiente " + (i + 1));
            taskList.add(task);
        }
    }

    @Test
    public void testGetAllTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(taskList);
        // Act
        List<TaskEntity> result = taskService.getAllTasks();
        // Assert
        assertEquals(10, result.size());
    }

    @Test
    public void testGetTaskById() {

        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskList.get(0)));
        // Act
        TaskEntity result = taskService.getTaskById(1L);
        // Assert
        assertEquals(taskList.get(0), result);
    }

    @Test
    public void testCreateTask() {
        // Arrange
        TaskEntity task = new TaskEntity();
        task.setId(11L);
        task.setTitle("title 11");
        task.setDescription("description 11");
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task.setStatus("pending 11");

        when(taskRepository.save(task)).thenReturn(task);

        // Act
        TaskEntity result = taskService.createTask(task);

        // Assert
        assertNotNull(result);
        assertEquals(11L, result.getId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testCreateTaskWithDatabaseError() {
        // Arrange
        TaskEntity task = new TaskEntity();

        when(taskRepository.save(task)).thenThrow(ConstraintViolationException.class);

        // Act y Assert
        assertThrows(ConstraintViolationException.class, () -> {
            taskService.createTask(task);
        });
    }

    @Test
    public void testEditTask() {
        // Arrange
        Long taskId = 1L;
        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(taskId);
        existingTask.setTitle("Old Title");

        TaskEntity updatedTask = new TaskEntity();
        updatedTask.setId(taskId);
        updatedTask.setTitle("New Title");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        // Act
        TaskEntity result = taskService.updateTask(taskId, updatedTask);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("New Title", result.getTitle());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        Long taskId = 1L;
        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(taskId);
        existingTask.setTitle("Task to be deleted");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(existingTask);
    }

}