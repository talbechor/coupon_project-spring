package com.coupon.coupon_projectspring.advice;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExistenceAdvice {
    @ExceptionHandler(value = {NotExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleNotExistException(Exception err) {
        return new ErrorDetails("Not Exist Error", err.getMessage());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleAlreadyExistsException(Exception err) {
        return new ErrorDetails("Already Exists Error", err.getMessage());
    }
}
