package com.app.taskcontrol.controller;

import com.app.taskcontrol.entity.TaskEntity;
import com.app.taskcontrol.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/tasks")
@Api(value = "Task Controller", description = "Controller for task management.")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "Allows you to obtain a list of tasks by applying pagination and ordering")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TaskEntity>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") boolean ascending) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, "dueDate"));
        Page<TaskEntity> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok().body(tasks);
    }

    @ApiOperation(value = "Allows you to search for a Task by Id in the database")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) {
        TaskEntity task = taskService.getTaskById(id);
        return ResponseEntity.ok().body(task);
    }

    @ApiOperation(value = "Allows you to insert a task into the database")
    @ApiResponses({ @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request") })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskEntity task) {
        TaskEntity createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @ApiOperation(value = "Allows you to update a Task by Id in the database")
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Task not found.")})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskEntity task) {
        TaskEntity updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok().body(updatedTask);
    }

    @ApiOperation(value = "Allows you to delete a Task from the database")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Task not found.") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
