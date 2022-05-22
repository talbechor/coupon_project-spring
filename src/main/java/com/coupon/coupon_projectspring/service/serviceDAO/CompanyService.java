package com.coupon.coupon_projectspring.service.serviceDAO;
import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;

import java.util.List;

public interface CompanyService {
    boolean login(String email, String password) throws NotExistsException;

    void addCoupon(Coupon coupon) throws AlreadyExistsException;

    void updateCoupon(Coupon coupon) throws NotExistsException;

    void deleteCoupon(int couponID) throws NotExistsException;

    List<Coupon> getCompanyCoupons() throws NotExistsException;

    List<Coupon> getCompanyCoupons(Categories category) throws NotExistsException;

    List<Coupon> getCompanyCoupons(double maxPrice) throws NotExistsException;

    Company getCompanyDetails();
}
