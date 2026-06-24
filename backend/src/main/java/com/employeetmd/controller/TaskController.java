package com.employeetmd.controller;

import com.employeetmd.dto.GenericResponse;
import com.employeetmd.dto.TaskRequest;
import com.employeetmd.dto.TaskResponse;
import com.employeetmd.dto.TaskStatusUpdateRequest;
import com.employeetmd.enums.Priority;
import com.employeetmd.enums.TaskStatus;
import com.employeetmd.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<GenericResponse<Page<TaskResponse>>> getAllTasks(
            @RequestParam(required = false) Long assignedEmployeeId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "dueDate", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<TaskResponse> tasks = taskService.getAllTasks(
                assignedEmployeeId, status, priority, search, pageable);
        return ResponseEntity.ok(GenericResponse.success("Tasks fetched successfully", tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<TaskResponse>> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(GenericResponse.success("Task fetched successfully", task));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskRequest request) {
        TaskResponse task = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.success("Task created successfully", task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<TaskResponse>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        TaskResponse task = taskService.updateTask(id, request);
        return ResponseEntity.ok(GenericResponse.success("Task updated successfully", task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(GenericResponse.success("Task deleted successfully", null));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<GenericResponse<TaskResponse>> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusUpdateRequest request) {
        TaskResponse task = taskService.updateTaskStatus(id, request);
        return ResponseEntity.ok(GenericResponse.success("Task status updated successfully", task));
    }
}
