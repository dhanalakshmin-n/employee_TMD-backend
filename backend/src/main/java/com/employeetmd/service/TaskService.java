package com.employeetmd.service;

import com.employeetmd.dto.TaskRequest;
import com.employeetmd.dto.TaskResponse;
import com.employeetmd.dto.TaskStatusUpdateRequest;
import com.employeetmd.entity.Employee;
import com.employeetmd.entity.Task;
import com.employeetmd.enums.Priority;
import com.employeetmd.enums.TaskStatus;
import com.employeetmd.exception.ResourceNotFoundException;
import com.employeetmd.repository.EmployeeRepository;
import com.employeetmd.repository.TaskRepository;
import com.employeetmd.repository.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(
            Long assignedEmployeeId,
            TaskStatus status,
            Priority priority,
            String search,
            Pageable pageable) {

        Specification<Task> spec = TaskSpecification.withFilters(
                assignedEmployeeId, status, priority, search);
        return taskRepository.findAll(spec, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = findTaskOrThrow(id);
        return toResponse(task);
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Employee employee = findEmployeeOrThrow(request.getAssignedEmployeeId());

        Task task = new Task();
        applyRequestToTask(task, request, employee);
        task.setStatus(TaskStatus.PENDING);   // initially the status of all tasks will be pending

        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = findTaskOrThrow(id);
        Employee employee = findEmployeeOrThrow(request.getAssignedEmployeeId());

        applyRequestToTask(task, request, employee);

        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long id, TaskStatusUpdateRequest request) {
        Task task = findTaskOrThrow(id);
        task.setStatus(request.getStatus());
        return toResponse(taskRepository.save(task));
    }

    private void applyRequestToTask(Task task, TaskRequest request, Employee employee) {
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription().trim());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setAssignedEmployee(employee);
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    private Employee findEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private TaskResponse toResponse(Task task) {
        Employee assignedEmployee = task.getAssignedEmployee();
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .assignedEmployeeId(assignedEmployee.getId())
                .assignedEmployeeName(assignedEmployee.getName())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
