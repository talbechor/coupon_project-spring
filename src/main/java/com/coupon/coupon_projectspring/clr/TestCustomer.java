package com.coupon.coupon_projectspring.clr;

import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.LoginManager;
import com.coupon.coupon_projectspring.repository.CategoryRepository;
import com.coupon.coupon_projectspring.repository.CompanyRepository;
import com.coupon.coupon_projectspring.repository.CouponRepository;
import com.coupon.coupon_projectspring.service.CustomerServiceIml;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
@Order(4)
public class TestCustomer implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;
    private final CouponRepository couponRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // customer login
        CustomerServiceIml customerService = (CustomerServiceIml) loginManager.login(new UserDetails("tal.bechor@com","0608",ClientType.CUSTOMER));


        // purchase coupon
        purchaseCoupon(customerService);

        // all customers coupons
        //  TablePrinter.print(customerService.getCustomerCoupons());

        // all customers coupons by category
      //   TablePrinter.print(customerService.getCustomerCouponsByCategory(Categories.VACATION));

        // all customers coupons by max price
        //  TablePrinter.print(customerService.getCustomerCouponsByMaxPrice(50));

    }

    private void purchaseCoupon(CustomerServiceIml customerService) throws AlreadyExistsException, NotExistsException {
        customerService.purchaseCoupon(2);
        customerService.purchaseCoupon(1);
    }
}
