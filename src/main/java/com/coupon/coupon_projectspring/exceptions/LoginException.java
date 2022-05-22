package com.coupon.coupon_projectspring.exceptions;

import com.coupon.coupon_projectspring.beans.ClientType;

public class LoginException extends Exception{
    public LoginException() {
        super("Invalid User !! ");
    }

    public LoginException(ClientType clientType) {
        super(clientType+ " Invalid User !!");
    }
}
