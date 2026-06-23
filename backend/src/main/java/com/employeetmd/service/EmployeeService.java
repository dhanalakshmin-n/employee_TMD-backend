package com.employeetmd.service;

import com.employeetmd.dto.EmployeeOptionResponse;
import com.employeetmd.dto.EmployeeRequest;
import com.employeetmd.dto.EmployeeResponse;
import com.employeetmd.entity.Employee;
import com.employeetmd.exception.ResourceNotFoundException;
import com.employeetmd.repository.EmployeeRepository;
import com.employeetmd.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getAllEmployees(String search, Pageable pageable) {
        Page<Employee> page;
        if (StringUtils.hasText(search)) {
            page = employeeRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search.trim(), search.trim(), pageable);
        } else {
            page = employeeRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = findEmployeeOrThrow(id);
        return toResponse(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeOptionResponse> getEmployeeOptions() {
        return employeeRepository.findAllByOrderByNameAsc().stream()
                .map(employee -> new EmployeeOptionResponse(employee.getId(), employee.getName()))
                .toList();
    }

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmailIgnoreCase(request.getEmail().trim())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setName(request.getName().trim());
        employee.setEmail(request.getEmail().trim());
        employee.setDepartment(request.getDepartment().trim());

        return toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = findEmployeeOrThrow(id);

        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail().trim(), id)) {
            throw new IllegalArgumentException("Email already exists");
        }

        employee.setName(request.getName().trim());
        employee.setEmail(request.getEmail().trim());
        employee.setDepartment(request.getDepartment().trim());

        return toResponse(employeeRepository.save(employee));
    }

    @Transactional
    public void deleteEmployee(Long id) {
        findEmployeeOrThrow(id);

        if (taskRepository.existsByAssignedEmployeeId(id)) {
            throw new IllegalArgumentException("Cannot delete employee with assigned tasks");
        }

        employeeRepository.deleteById(id);
    }

    private Employee findEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .createdAt(employee.getCreatedAt())
                .build();
    }
}
