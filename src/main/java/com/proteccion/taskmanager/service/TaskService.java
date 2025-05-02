package com.proteccion.taskmanager.service;

import com.proteccion.taskmanager.dto.TaskDTO;
import com.proteccion.taskmanager.exception.ResourceNotFoundException;
import com.proteccion.taskmanager.model.*;
import com.proteccion.taskmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;

    public List<TaskDTO> getTasksForUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        List<Task> tasks = taskRepository.findByOwnerOrAssignee(user, user, Sort.by("dueDate"));
        return tasks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public TaskDTO getTaskByIdForUser(Long id, String username) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return mapToDto(task);
    }

    public TaskDTO createTask(TaskDTO dto, String username) {
        User owner = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(TaskStatus.POR_HACER);
        task.setOwner(owner);
        if (dto.getAssigneeId() != null) {
            User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getAssigneeId()));
            task.setAssignee(assignee);
        }
        Task saved = taskRepository.save(task);
        return mapToDto(saved);
    }

    public TaskDTO updateTask(Long id, TaskDTO dto, String username) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(TaskStatus.valueOf(dto.getStatus()));
        if (dto.getAssigneeId() != null) {
            User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getAssigneeId()));
            task.setAssignee(assignee);
        }
        Task updated = taskRepository.save(task);
        return mapToDto(updated);
    }

    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
    }

    private TaskDTO mapToDto(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus().name());
        dto.setAssigneeId(task.getAssignee() != null ? task.getAssignee().getId() : null);
        return dto;
    }
}
