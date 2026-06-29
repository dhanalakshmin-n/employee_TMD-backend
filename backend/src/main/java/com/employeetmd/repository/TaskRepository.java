package com.employeetmd.repository;

import com.employeetmd.entity.Task;
import com.employeetmd.enums.Priority;
import com.employeetmd.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    long countByStatus(TaskStatus status);

    long countByAssignedEmployeeId(Long employeeId);

    long countByAssignedEmployeeIdAndStatus(Long assignedEmployeeId, TaskStatus status);

    boolean existsByAssignedEmployeeId(Long employeeId);
}
