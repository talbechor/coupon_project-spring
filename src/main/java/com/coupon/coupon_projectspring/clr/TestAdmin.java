package com.coupon.coupon_projectspring.clr;


import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.login.LoginManager;
import com.coupon.coupon_projectspring.repository.CustomerRepository;
import com.coupon.coupon_projectspring.service.AdminServiceIml;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


//@Component
@Order(2)
@RequiredArgsConstructor
public class TestAdmin implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {



        // login admin
       AdminServiceIml adminService = (AdminServiceIml) loginManager.login(new UserDetails("admin@admin.com","admin",ClientType.ADMIN));

        // add company
        add3company(adminService);

        //update company
        updateCompany(adminService);

        // delete company
        // deleteCompany(adminService);

        // add customer
        add3Customer(adminService);

        // update customer
        updateCustomer(adminService);

        // delete customer
        //   deleteCustomer(adminService);

    }

    private void add3company(AdminServiceIml adminService) throws AlreadyExistsException {
        adminService.addCompany(Company.builder()
                .email("max.com")
                .name("max")
                .password("123")
                .build());


        adminService.addCompany(Company.builder()
                .email("facebook.com")
                .name("facebook")
                .password("2345")
                .build());


        adminService.addCompany(Company.builder()
                .email("BIG.COM")
                .password("222")
                .name("big")
                .build());
    }

    private void updateCompany(AdminServiceIml adminService) throws NotExistsException {
        adminService.updateCompany(Company.builder()
                .password("1234")
                .id(1)
                .name("max")
                .email("max.com")
                .build());

    }

    private void deleteCompany(AdminServiceIml adminService) throws NotExistsException {
        adminService.deleteCompany(1);
    }

    private void add3Customer(AdminServiceIml adminService) throws AlreadyExistsException {
        adminService.addCustomer(Customer.builder()
                .firstName("tal")
                .lastName("bechor")
                .password("0608")
                .email("tal.com")
                .build());

        adminService.addCustomer(Customer.builder()
                .firstName("bar")
                .lastName("bechor")
                .password("0604")
                .email("bar.com")
                .build());


        adminService.addCustomer(Customer.builder()
                .email("LEE.com")
                .password("333")
                .firstName("lee")
                .lastName("bechor")
                .build());

    }

    private void updateCustomer(AdminServiceIml adminService) throws NotExistsException {
        adminService.updateCustomer(Customer.builder()
                .email("tal.bechor@com")
                .password("0608")
                .lastName("bechor")
                .firstName("tal")
                .id(1)
                .build());

    }

    private void deleteCustomer(AdminServiceIml adminService) throws NotExistsException {
        adminService.deleteCustomer(2);

    }
}
