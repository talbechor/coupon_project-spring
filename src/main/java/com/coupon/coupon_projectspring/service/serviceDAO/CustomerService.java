package com.coupon.coupon_projectspring.service.serviceDAO;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;

import java.util.List;

public interface CustomerService {
    boolean login(String email, String password) throws NotExistsException;

    void purchaseCoupon(Coupon coupon) throws AlreadyExistsException, NotExistsException;

    List<Coupon> getCustomerCoupons() throws NotExistsException;

    List<Coupon> getCustomerCouponsByCategory(Categories category) throws NotExistsException;

    List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws NotExistsException;

    Customer getCustomerDetails();

}
