package com.coupon.coupon_projectspring.clr;



import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;

import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.login.LoginManager;


import com.coupon.coupon_projectspring.repository.CategoryRepository;
import com.coupon.coupon_projectspring.repository.CompanyRepository;
import com.coupon.coupon_projectspring.service.CompanyServiceIml;
import com.coupon.coupon_projectspring.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.time.LocalDate;

//@Component
@RequiredArgsConstructor
@Order(3)
public class TestCompany implements CommandLineRunner {
    @Autowired
    private LoginManager loginManager;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        CompanyServiceIml companyService = (CompanyServiceIml) loginManager.login(new UserDetails("max.com","1234",ClientType.COMPANY));

        // add coupons
         add3Coupons(companyService);

        // update Coupon
       updateCoupon(companyService);

        // delete coupon
       // deleteCoupon(companyService);


        // get companies coupons
      //  TablePrinter.print(companyService.getCompanyCoupons());

        // get companies coupons by category
        //   TablePrinter.print(companyService.getCompanyCoupons(Categories.VACATION));

        // get companies coupons by max price
        //  TablePrinter.print(companyService.getCompanyCoupons(50));

        // get company details
        //    TablePrinter.print(companyService.getCompanyDetails());


    }

    private void add3Coupons(CompanyServiceIml companyService) throws AlreadyExistsException {
        companyService.addCoupon(Coupon.builder()
                .company(companyService.getCompanyDetails())
                .category(categoryRepository.getById(1))
                .amount(25)
                .price(20)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .description("pizza")
                .title("personal pizza")
                .image("pizza.com")
                .build());


        companyService.addCoupon(Coupon.builder()
                .company(companyRepository.getById(1))
                .category(categoryRepository.getById(4))
                .title("hotel")
                .description("dan hotel")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(12)))
                .amount(10)
                .price(1000)
                .image("dan.com")
                .build());

        companyService.addCoupon(Coupon.builder()
                .company(companyRepository.getById(1))
                .category(categoryRepository.getById(3))
                .title("salad")
                .description("mix olive")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .amount(2)
                .price(20)
                .image("salad.com")
                .build());

    }

    private void updateCoupon(CompanyServiceIml companyService) throws NotExistsException {
        companyService.updateCoupon((Coupon.builder()
                .id(1)
                .company(companyService.getCompanyDetails())
                .category(categoryRepository.getById(1))
                .amount(25)
                .price(30)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .description("pizza")
                .title("personal pizza")
                .image("pizza.com"))
                .build());
    }

    private void deleteCoupon(CompanyServiceIml companyService) throws NotExistsException {
        companyService.deleteCoupon(2);
    }
}
