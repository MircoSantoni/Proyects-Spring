package com.mirco.curso.springboot.error.springboot_error.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.mirco.curso.springboot.error.springboot_error.models.Error;


@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Error> divisionByZero(Exception ex) {

        Error error = new Error();
        error.setDate(new Date());
        error.setError("Error divison x 0");
        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //return ResponseEntity.internalServerError().body(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> notFoundEx (NoHandlerFoundException e) {

        Error error = new Error();
        error.setDate(new Date());
        error.setError("Api/Rest no encontrado");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(NumberFormatException.class)
    public Map<String, Object> numberFormatException(Exception ex){
        Map <String, Object> error = new HashMap<>();

        error.put("date", new Date());
        error.put("error", "Numero incorrecto, no tiene buen formato");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }

    @ExceptionHandler({NullPointerException.class, HttpMessageNotWritableException.class})
    public Map<String, Object> userNotFoundExMap(Exception ex){
        Map <String, Object> error = new HashMap<>();

        error.put("date", new Date());
        error.put("error", "El usuario o el rol no existe");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return error;
    }
    

}   