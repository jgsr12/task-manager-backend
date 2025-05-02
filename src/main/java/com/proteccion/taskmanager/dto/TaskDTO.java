package com.proteccion.taskmanager.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TaskDTO", description = "Datos de una tarea colaborativa")
public class TaskDTO {

    @Schema(description = "ID único de la tarea", example = "42", required = true)
    private Long id;

    @Schema(description = "Título de la tarea", example = "Preparar demo", required = true)
    private String title;

    @Schema(description = "Descripción detallada de la tarea", example = "Crear un prototipo funcional para la presentación", required = true)
    private String description;

    @Schema(description = "Fecha de vencimiento de la tarea en formato ISO (YYYY-MM-DD)", example = "2025-05-15", type = "string", format = "date", required = true)
    private LocalDate dueDate;

    @Schema(description = "Estado de la tarea (p.ej. PENDING, COMPLETED)", example = "PENDING")
    private String status;

    @Schema(description = "ID del usuario asignado a la tarea", example = "1001")
    private Long assigneeId;

    public TaskDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
}
