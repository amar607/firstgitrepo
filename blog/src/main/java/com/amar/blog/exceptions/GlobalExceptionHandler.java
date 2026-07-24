package com.amar.blog.exceptions;

import com.amar.blog.dto.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), resourceNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    private ResponseEntity<ErrorDetails> handleResourceNotFoundException(BlogAPIException blogAPIException,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), blogAPIException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorDetails> handleResourceNotFoundException(AccessDeniedException accessDeniedException,
                                                                         WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), accessDeniedException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleResourceNotFoundException(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                         WebRequest webRequest) {

        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        //ErrorDetails errorDetails = new ErrorDetails(new Date(), methodArgumentNotValidException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorDetails> handleDefaultException(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        exception.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
