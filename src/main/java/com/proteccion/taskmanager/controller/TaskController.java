package com.proteccion.taskmanager.controller;

import com.proteccion.taskmanager.dto.TaskDTO;
import com.proteccion.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tareas", description = "Operaciones para gestionar tareas colaborativas")
@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
      summary = "Listar todas las tareas",
      description = "Devuelve la lista de tareas asociadas al usuario autenticado",
      responses = {
        @ApiResponse(responseCode = "200", description = "Listado de tareas obtenido",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado")
      }
    )
    @GetMapping
    public List<TaskDTO> getAllTasks(
      @Parameter(hidden = true) Authentication authentication
    ) {
        return taskService.getTasksForUser(authentication.getName());
    }

    @Operation(
      summary = "Obtener una tarea por ID",
      description = "Devuelve los detalles de una tarea específica del usuario autenticado",
      responses = {
        @ApiResponse(responseCode = "200", description = "Tarea encontrada",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
      }
    )
    @GetMapping("/{id}")
    public TaskDTO getTaskById(
      @Parameter(
        in = ParameterIn.PATH,
        name = "id",
        description = "ID de la tarea",
        required = true,
        schema = @Schema(type = "integer", format = "int64", example = "42")
      )
      @PathVariable Long id,
      @Parameter(hidden = true) Authentication authentication
    ) {
        return taskService.getTaskByIdForUser(id, authentication.getName());
    }

    @Operation(
      summary = "Crear una nueva tarea",
      description = "Crea una tarea con los datos proporcionados y la asocia al usuario autenticado",
      responses = {
        @ApiResponse(responseCode = "201", description = "Tarea creada",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
      }
    )
    @PostMapping
    public TaskDTO createTask(
      @Parameter(
        description = "Objeto TaskDTO con los datos de la tarea",
        required = true,
        content = @Content(schema = @Schema(implementation = TaskDTO.class))
      )
      @RequestBody TaskDTO taskDTO,
      @Parameter(hidden = true) Authentication authentication
    ) {
        return taskService.createTask(taskDTO, authentication.getName());
    }

    @Operation(
      summary = "Actualizar una tarea",
      description = "Actualiza los campos de una tarea existente del usuario autenticado",
      responses = {
        @ApiResponse(responseCode = "200", description = "Tarea actualizada",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TaskDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
      }
    )
    @PutMapping("/{id}")
    public TaskDTO updateTask(
      @Parameter(
        in = ParameterIn.PATH,
        name = "id",
        description = "ID de la tarea a actualizar",
        required = true,
        schema = @Schema(type = "integer", format = "int64", example = "42")
      )
      @PathVariable Long id,
      @Parameter(
        description = "Objeto TaskDTO con los nuevos datos",
        required = true,
        content = @Content(schema = @Schema(implementation = TaskDTO.class))
      )
      @RequestBody TaskDTO taskDTO,
      @Parameter(hidden = true) Authentication authentication
    ) {
        return taskService.updateTask(id, taskDTO, authentication.getName());
    }

    @Operation(
      summary = "Eliminar una tarea",
      description = "Borra la tarea indicada si pertenece al usuario autenticado",
      responses = {
        @ApiResponse(responseCode = "200", description = "Tarea eliminada"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
      }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
      @Parameter(
        in = ParameterIn.PATH,
        name = "id",
        description = "ID de la tarea a eliminar",
        required = true,
        schema = @Schema(type = "integer", format = "int64", example = "42")
      )
      @PathVariable Long id,
      @Parameter(hidden = true) Authentication authentication
    ) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
