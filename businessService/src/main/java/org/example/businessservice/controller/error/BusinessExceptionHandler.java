package org.example.businessservice.controller.error;

import org.example.businessservice.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(ValidacionNegocioException.class)
    public ResponseEntity<?> bad(ValidacionNegocioException e){ return ResponseEntity.badRequest().body(Map.of("error",e.getMessage())); }
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<?> notFound(ProductoNoEncontradoException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage())); }
    @ExceptionHandler(MicroserviceCommunicationException.class)
    public ResponseEntity<?> comm(MicroserviceCommunicationException e){ return ResponseEntity.status(502).body(Map.of("error",e.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> val(MethodArgumentNotValidException e){ return ResponseEntity.badRequest().body(Map.of("error","Validación")); }
}