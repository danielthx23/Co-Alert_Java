package br.com.fiap.CoAlert.exception;

import br.com.fiap.CoAlert.dto.response.ApiResponseGeneric;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleNotFound(EntityNotFoundException ex) {
        ApiResponseGeneric<Object> response = new ApiResponseGeneric<>();
        response.setMensagem("Recurso não encontrado: " + ex.getMessage());
        response.setDados(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponseGeneric<Object> response = new ApiResponseGeneric<>();
        response.setMensagem("Requisição inválida: " + ex.getMessage());
        response.setDados(null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleIllegalState(IllegalStateException ex) {
        ApiResponseGeneric<Object> response = new ApiResponseGeneric<>();
        response.setMensagem("Conflito de estado: " + ex.getMessage());
        response.setDados(null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String message = ex.getRootCause() != null
                ? ex.getRootCause().getMessage()
                : "Violação de integridade dos dados.";
        ApiResponseGeneric<Object> response = new ApiResponseGeneric<>();
        response.setMensagem("Erro de integridade: " + message);
        response.setDados(null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseGeneric<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponseGeneric<Map<String, String>> response = new ApiResponseGeneric<>();
        response.setMensagem("Erro de validação nos campos.");
        response.setDados(errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseGeneric<Object>> handleGeneric(Exception ex) {
        ApiResponseGeneric<Object> response = new ApiResponseGeneric<>();
        response.setMensagem("Erro interno do servidor. Por favor, tente novamente mais tarde.");
        response.setDados(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
