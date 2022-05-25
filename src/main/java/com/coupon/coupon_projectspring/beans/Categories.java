package com.coupon.coupon_projectspring.beans;

public enum Categories {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    VACATION;

    public final int VALUE = 1 + ordinal();
    public int getValue() {
        return VALUE;
    }
}
