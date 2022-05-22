package com.coupon.coupon_projectspring.advice;
import com.coupon.coupon_projectspring.exceptions.TokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class TokenAdvice {
    @ExceptionHandler(value = {TokenException.class, MalformedJwtException.class, ExpiredJwtException.class
            , SignatureException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleTokenException(Exception err) {
        return new ErrorDetails("Error Token", "Unauthorized , invalid Token ! ");
    }
}
