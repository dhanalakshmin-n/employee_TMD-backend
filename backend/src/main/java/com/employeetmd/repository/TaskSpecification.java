package com.employeetmd.repository;

import com.employeetmd.entity.Task;
import com.employeetmd.enums.Priority;
import com.employeetmd.enums.TaskStatus;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class TaskSpecification {

    private TaskSpecification() {
    }

    public static Specification<Task> withFilters(
            Long assignedEmployeeId,
            TaskStatus status,
            Priority priority,
            String search) {

        return (root, query, cb) -> {
            if (query != null && Task.class.equals(query.getResultType())) {
                root.fetch("assignedEmployee", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();

            if (assignedEmployeeId != null) {
                predicates.add(cb.equal(root.get("assignedEmployee").get("id"), assignedEmployeeId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }
            if (StringUtils.hasText(search)) {
                predicates.add(cb.like(
                        cb.lower(root.get("title")),
                        "%" + search.trim().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
