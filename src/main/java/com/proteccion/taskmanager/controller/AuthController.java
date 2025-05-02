package com.proteccion.taskmanager.controller;

import com.proteccion.taskmanager.dto.JwtResponse;
import com.proteccion.taskmanager.dto.LoginRequest;
import com.proteccion.taskmanager.dto.MessageResponse;
import com.proteccion.taskmanager.dto.SignupRequest;
import com.proteccion.taskmanager.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticación", description = "Operaciones de login y registro de usuarios")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica al usuario con username y password, devuelve un JWT en JwtResponse",
        responses = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JwtResponse.class)
                )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(
        @RequestBody(description = "Datos de acceso del usuario", required = true,
            content = @Content(schema = @Schema(implementation = LoginRequest.class)))
        @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @Operation(
        summary = "Registrar usuario",
        description = "Crea un nuevo usuario con los datos provistos en SignupRequest",
        responses = {
            @ApiResponse(responseCode = "201", description = "Registro exitoso",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageResponse.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (correo o username ya existe)")
        }
    )
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(
        @RequestBody(description = "Datos para registro de usuario", required = true,
            content = @Content(schema = @Schema(implementation = SignupRequest.class)))
        @org.springframework.web.bind.annotation.RequestBody SignupRequest signupRequest) {
        return authService.registerUser(signupRequest);
    }
}