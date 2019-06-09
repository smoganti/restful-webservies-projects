package com.resources.rest.webservices.restfulwebservicesproject1.com.exception;

import com.resources.rest.webservices.restfulwebservicesproject1.com.socialnetwork.resources.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {

        ExceptionResource exceptionResource = new ExceptionResource(new Date(),ex.getMessage(),request.getDescription(false));

        return new ResponseEntity(exceptionResource, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {

        ExceptionResource exceptionResource = new ExceptionResource(new Date(),ex.getMessage(),request.getDescription(false));

        return new ResponseEntity(exceptionResource, HttpStatus.NOT_FOUND);
    }

    }
