package com.rocketseat.planner.infra.middleware;

import com.rocketseat.planner.core.ErrorResponse;
import com.rocketseat.planner.domain.errors.NotFoundResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = createErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundResourceException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = createErrorResponse(ex, request, HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ErrorResponse errorResponse = createErrorResponse(ex, request, HttpStatus.BAD_REQUEST, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorResponse = createErrorResponse(ex, request, HttpStatus.BAD_REQUEST, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> messages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = createErrorResponse(ex, request, HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(Exception ex, WebRequest request, HttpStatus status, List<String> messages) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .messages(messages)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
    }
}
