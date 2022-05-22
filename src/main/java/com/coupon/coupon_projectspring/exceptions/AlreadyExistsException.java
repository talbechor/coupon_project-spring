package com.coupon.coupon_projectspring.exceptions;

import com.coupon.coupon_projectspring.beans.ClientType;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(ClientType clientType) {
        super(clientType + " Already exist in the system");
    }
}
