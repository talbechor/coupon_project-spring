package com.coupon.coupon_projectspring.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@ControllerAdvice
public class InvalidInputAdvice {
    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleUniqueException(Exception err) {
        return new ErrorDetails("Unique Value Error", "Name, email or title already taken");
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleInvalidFormatException(Exception err) {
        return new ErrorDetails("Invalid Format Error",
                "Please make sure your input is valid (Date should be written in yyyy-mm-dd format)");
    }

    @ExceptionHandler(value = {PropertyValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handlePropertyValueException(Exception err) {
        return new ErrorDetails("Unassigned Value Error", "Please make sure you filled all of the properties");
    }

}
