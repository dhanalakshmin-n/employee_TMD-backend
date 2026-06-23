package com.employeetmd.controller;

import com.employeetmd.dto.EmployeeOptionResponse;
import com.employeetmd.dto.EmployeeRequest;
import com.employeetmd.dto.EmployeeResponse;
import com.employeetmd.dto.GenericResponse;
import com.employeetmd.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<GenericResponse<Page<EmployeeResponse>>> getAllEmployees(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<EmployeeResponse> employees = employeeService.getAllEmployees(search, pageable);
        return ResponseEntity.ok(GenericResponse.success("Employees fetched successfully", employees));
    }

    @GetMapping("/options")
    public ResponseEntity<GenericResponse<List<EmployeeOptionResponse>>> getEmployeeOptions() {
        List<EmployeeOptionResponse> options = employeeService.getEmployeeOptions();
        return ResponseEntity.ok(GenericResponse.success("Employee options fetched successfully", options));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(GenericResponse.success("Employee fetched successfully", employee));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse employee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.success("Employee created successfully", employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse employee = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(GenericResponse.success("Employee updated successfully", employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(GenericResponse.success("Employee deleted successfully", null));
    }
}
