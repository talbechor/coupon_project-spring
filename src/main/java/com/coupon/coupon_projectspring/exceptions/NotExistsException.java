package com.coupon.coupon_projectspring.exceptions;

import com.coupon.coupon_projectspring.beans.ClientType;

public class NotExistsException extends Exception{
    public NotExistsException() {
        super();
    }

    public NotExistsException(ClientType clientType) {
        super(clientType + " Not exist in the system" );
    }
}
