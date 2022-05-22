package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.repository.CategoryRepository;
import com.coupon.coupon_projectspring.repository.CompanyRepository;
import com.coupon.coupon_projectspring.repository.CouponRepository;
import com.coupon.coupon_projectspring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public abstract  class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CategoryRepository categoryRepository;


    public abstract boolean login(String email, String password) throws NotExistsException;
}
