package com.delivery.exception;

import com.delivery.dto.response.ErrorResponseDTO;
import com.delivery.dto.response.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Recurso não encontrado")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusiness(BusinessException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Violação de regra de negócio")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        Map<String, String> invalidFields = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        ValidationErrorDTO error = ValidationErrorDTO.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Erro de validação")
                .message("Um ou mais campos estão inválidos")
                .path(request.getRequestURI())
                .fields(invalidFields)
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidStatusTransition(InvalidStatusTransitionException e, jakarta.servlet.http.HttpServletRequest request) {
        org.springframework.http.HttpStatus status = org.springframework.http.HttpStatus.BAD_REQUEST;
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .timestamp(java.time.Instant.now())
                .status(status.value())
                .error("Transição de status inválida")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }

}
