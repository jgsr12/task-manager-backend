package com.proteccion.taskmanager.repository;

import com.proteccion.taskmanager.model.Task;
import com.proteccion.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerOrAssignee(User owner, User assignee, Sort sort);
}
