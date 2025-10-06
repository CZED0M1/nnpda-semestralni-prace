package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.exception.OwnershipException;
import cz.upce.fei.nnpda_app.exception.NotFoundException;
import cz.upce.fei.nnpda_app.exception.WorkflowException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // 404 - Neexistující endpoint
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return Map.of("error", "Endpoint not found: " + ex.getRequestURL());
    }

    // 400 - Nevalidní vstup
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Map.of("error", errorMessage);
    }

    // 404 - Uživatel nenalezen
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(NotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    // 409 - Duplicitní záznam
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleAlreadyExistsException(DuplicateKeyException ex) {
        return Map.of("error", ex.getMessage());
    }

    // 403 - Zakázaný přístup
    @ExceptionHandler(OwnershipException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleOwnershipException(OwnershipException ex) {
        return Map.of("error", ex.getMessage());
    }

    // 401 - Neautorizovaný přístup
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationException(AuthenticationException ex) {
        return Map.of("error", ex.getMessage());
    }

    // 422 - Nevalidní stav workflow (vlastní)
    @ExceptionHandler(WorkflowException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> handleWorkflowException(WorkflowException ex) {
        return Map.of("error", ex.getMessage());
    }

    // 400 - Špatný JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Map.of("error", "Invalid request body: " + ex.getMostSpecificCause().getMessage());
    }

    // 400 - Neplatný argument
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

    // 500 - Neošetřená chyba
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleNullPointerException(NullPointerException ex) {
        return Map.of("error", "Unexpected null value: " + ex.getMessage());
    }

}
