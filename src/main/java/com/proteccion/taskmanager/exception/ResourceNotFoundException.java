package com.proteccion.taskmanager.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s no encontrado con %s : '%s'", resource, field, value));
    }
}
