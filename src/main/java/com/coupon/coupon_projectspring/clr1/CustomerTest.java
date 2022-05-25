package com.coupon.coupon_projectspring.clr1;


import com.coupon.coupon_projectspring.service.CustomerServiceIml;
import com.coupon.coupon_projectspring.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;

import com.coupon.coupon_projectspring.beans.UserDetails;

import com.coupon.coupon_projectspring.service.LoginManager;

@Component
@RequiredArgsConstructor
@Order(3)
public class CustomerTest implements CommandLineRunner {
    private final LoginManager loginService;

    @Override
    public void run(String... args) throws Exception {
        CustomerServiceIml customerService = (CustomerServiceIml) loginService.login(
                new UserDetails("orenlevi6@gmail.com", "orenOren", ClientType.CUSTOMER));

        System.out.println("Purchase coupon");
        customerService.purchaseCoupon(1);
        TablePrinter.print(customerService.getCustomerCoupons());

        System.out.println("Get coupons by category - FOOD");
        TablePrinter.print(customerService.getCustomerCouponsByCategory(Categories.FOOD));

        System.out.println("Get coupons by max price - 50");
        TablePrinter.print(customerService.getCustomerCouponsByMaxPrice(50));

        System.out.println("Get customer #1 details");
//        TablePrinter.print(customerService.getCustomerDetails()); doesn't work - failed to lazily initialize
        System.out.println(customerService.getCustomerDetails());
    }

}
