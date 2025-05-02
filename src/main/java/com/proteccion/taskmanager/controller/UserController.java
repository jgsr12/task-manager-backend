package com.proteccion.taskmanager.controller;

import com.proteccion.taskmanager.dto.UserDTO;
import com.proteccion.taskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones de administración de usuarios")
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
      summary = "Listar todos los usuarios",
      description = "Devuelve la lista de todos los usuarios. Requiere rol ADMIN.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
      }
    )
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
      summary = "Obtener un usuario por ID",
      description = "Devuelve los detalles de un usuario específico. Requiere rol ADMIN.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
      }
    )
    @GetMapping("/{id}")
    public UserDTO getUserById(
      @Parameter(
        in = ParameterIn.PATH,
        name = "id",
        description = "ID del usuario",
        required = true,
        schema = @Schema(type = "integer", format = "int64", example = "123")
      )
      @PathVariable Long id
    ) {
        return userService.getUserById(id);
    }

    @Operation(
      summary = "Actualizar un usuario",
      description = "Modifica los datos de un usuario existente. Requiere rol ADMIN.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado",
          content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
      }
    )
    @PutMapping("/{id}")
    public UserDTO updateUser(
      @Parameter(
        in = ParameterIn.PATH,
        name = "id",
        description = "ID del usuario a actualizar",
        required = true,
        schema = @Schema(type = "integer", format = "int64", example = "123")
      )
      @PathVariable Long id,
      @Parameter(
        description = "Objeto UserDTO con los nuevos datos",
        required = true,
        content = @Content(schema = @Schema(implementation = UserDTO.class))
      )
      @RequestBody UserDTO userDTO
    ) {
        return userService.updateUser(id, userDTO);
    }

    @Operation(
      summary = "Eliminar un usuario",
      description = "Borra un usuario existente. Requiere rol ADMIN.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
      }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
      @Parameter(
        in = ParameterIn.PATH,
        name = "id",
        description = "ID del usuario a eliminar",
        required = true,
        schema = @Schema(type = "integer", format = "int64", example = "123")
      )
      @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
