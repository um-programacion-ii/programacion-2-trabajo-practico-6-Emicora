package org.example.dataservice.controller.error;

import org.example.dataservice.exception.RecursoNoEncontradoException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DataExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<?> notFound(RecursoNoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> badRequest(MethodArgumentNotValidException ex){
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(e->e.getField(), e->e.getDefaultMessage(), (a,b)->a));
        return ResponseEntity.badRequest().body(Map.of("error","Validación", "details", errors));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generic(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error","Interno"));
    }
}