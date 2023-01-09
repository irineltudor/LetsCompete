package com.example.letscompete.exceptionhandling;

import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.exception.DateNotValidException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandling {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleHibernateObjectValidation(MethodArgumentNotValidException exception){
        Map<String, Object> responseParameters = new HashMap<>();

        List<String> errors = exception.getBindingResult().getAllErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList());

        responseParameters.put("Reason : ", errors);
        responseParameters.put("DateTime : ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleHibernateEntityNotFound(EntityNotFoundException exception)
    {
        Map<String, Object> responseParameters = new HashMap<>();

        responseParameters.put("Reason : ", exception.getMessage());
        responseParameters.put("DateTime : ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(CannotDeleteEntityException.class)
    public ResponseEntity<?> handleHibernateCannotDeleteEntity(CannotDeleteEntityException exception)
    {
        Map<String, Object> responseParameters = new HashMap<>();

        responseParameters.put("Reason : ", exception.getMessage());
        responseParameters.put("DateTime : ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(DateNotValidException.class)
    public ResponseEntity<?> handleHibernateDateNotValid(DateNotValidException exception)
    {
        Map<String, Object> responseParameters = new HashMap<>();

        responseParameters.put("Reason : ", exception.getMessage());
        responseParameters.put("DateTime : ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleHibernateRuntimeException(RuntimeException exception)
    {
        Map<String, Object> responseParameters = new HashMap<>();

        responseParameters.put("Reason : ", exception.getMessage());
        responseParameters.put("DateTime : ", LocalDateTime.now().toString());

        return ResponseEntity.badRequest().body(responseParameters);
    }
}
