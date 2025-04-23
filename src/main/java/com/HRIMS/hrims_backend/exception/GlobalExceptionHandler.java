package com.HRIMS.hrims_backend.exception;

import com.HRIMS.hrims_backend.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ArrayList<String>>> handleResourceNotFoundException(ResourceNotFoundException ex){

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiResponse<ArrayList<String>> apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Resource not found.",
                HttpStatus.NOT_FOUND.name(), LocalDateTime.now(), new ArrayList<>(), errors);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<ArrayList<String>>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiResponse<ArrayList<String>> apiResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "Resource already exists.",
                HttpStatus.CONFLICT.name(), LocalDateTime.now(), new ArrayList<>(), errors);

        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<ArrayList<String>>> handleInvalidPasswordException(InvalidPasswordException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiResponse<ArrayList<String>> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid Password.",
                HttpStatus.BAD_REQUEST.name(), LocalDateTime.now(), new ArrayList<>(), errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ArrayList<String>>> handleGlobalException(Exception ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiResponse<ArrayList<String>> apiResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception",
                HttpStatus.INTERNAL_SERVER_ERROR.name(), LocalDateTime.now(), new ArrayList<>(), errors);

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ArrayList<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through all errors and store them in a map
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        String combinedMessages = errors.values().stream()
                .collect(Collectors.joining("; "));

        List<String> errorList = new ArrayList<>();
        errorList.add(combinedMessages);
        ApiResponse<ArrayList<String>> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                HttpStatus.BAD_REQUEST.name(), LocalDateTime.now(), new ArrayList<>(), errorList);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
