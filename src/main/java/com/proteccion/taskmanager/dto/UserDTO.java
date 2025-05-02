package com.proteccion.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(name = "UserDTO", description = "Datos de usuario para administración")
public class UserDTO {

    @Schema(description = "ID único del usuario", example = "123", required = true)
    private Long id;

    @Schema(description = "Nombre de usuario", example = "jdoe", required = true)
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "jdoe@example.com", required = true)
    private String email;

    @Schema(description = "Roles asignados al usuario", example = "[\"ADMIN\", \"USER\"]")
    private Set<String> roles;

    public UserDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
