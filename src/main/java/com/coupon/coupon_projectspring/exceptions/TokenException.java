package com.coupon.coupon_projectspring.exceptions;

public class TokenException extends Exception{
    public TokenException() {
        super("Invalid Token !");
    }

    public TokenException(String message) {
        super(message);
    }
}
