package com.coupon.coupon_projectspring.advice;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class LoginAdvice {
    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleLoginException(Exception err) {
        return new ErrorDetails("Error login", err.getMessage());
    }

}
