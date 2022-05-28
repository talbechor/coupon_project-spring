package com.coupon.coupon_projectspring.clr;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.repository.CategoryRepository;
import com.coupon.coupon_projectspring.repository.CouponRepository;
import com.coupon.coupon_projectspring.service.CompanyServiceIml;
import com.coupon.coupon_projectspring.service.LoginManager;

import com.coupon.coupon_projectspring.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(2)
public class CompanyTest implements CommandLineRunner {
    private final CategoryRepository categoryRepo;
    private final CouponRepository couponRepo;
    private final LoginManager loginService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Company Test \n");

        System.out.println("Company 1");
        companyTest1();

        System.out.println("Company 2");
        companyTest2();
    }

    private void companyTest1() throws Exception {
        CompanyServiceIml company1 = (CompanyServiceIml) loginService.login(
                new UserDetails("no-reply@oren.inc", "orenInc", ClientType.COMPANY));

        company1.addCoupon((Coupon .builder()
//                .company(company1.getCompanyDetails())
                .category(categoryRepo.getById(1))
                .title("Mcdonald's")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(19.90)
                .image("M")
                .build()));

        company1.addCoupon(Coupon.builder()
//                .company(company1.getCompanyDetails())
                .category(categoryRepo.getById(2))
                .title("Oven Discount")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(29.90)
                .image("Oven")
                .build());

        System.out.println("Adding coupons");
        TablePrinter.print(company1.getCompanyCoupons());

        System.out.println("Updating coupon #1");
        company1.updateCoupon(Coupon.builder()
                .id(1)
//                .company(company1.getCompanyDetails())
                .category(categoryRepo.getById(1))
                .title("Mcdonald's")
                .description("BLAH")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(19.90)
                .image("M")
                .build());

        TablePrinter.print(company1.getCompanyCoupons());

        System.out.println("Print all company coupons by category - FOOD");
        TablePrinter.print(company1.getCompanyCoupons(Categories.FOOD));

        System.out.println("Print all company coupons by max price - 40");
        TablePrinter.print(company1.getCompanyCoupons(40));

        System.out.println("Deleting coupon #2");
        company1.deleteCoupon(2);
        TablePrinter.print(company1.getCompanyCoupons());

        System.out.println("Get company #1 details");
//        TablePrinter.print(company1.getCompanyDetails()); doesn't work - failed to lazily initialize
        System.out.println(company1.getCompanyDetails() + "\n");
    }

    private void companyTest2() throws Exception {
        CompanyServiceIml company2 = (CompanyServiceIml) loginService.login(
                new UserDetails("tal@gmail.inc", "taltal", ClientType.COMPANY));

        company2.addCoupon(Coupon.builder()
//                .company(company2.getCompanyDetails())
                .category(categoryRepo.getById(1))
                .title("Food Discount")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now()))
                .amount(10)
                .price(39.90)
                .image("Food")
                .build());

        company2.addCoupon(Coupon.builder()
//                .company(company2.getCompanyDetails())
                .category(categoryRepo.getById(2))
                .title("Oven Discount")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .amount(10)
                .price(34.90)
                .image("Oven")
                .build());

        System.out.println("Adding coupons");
        TablePrinter.print(company2.getCompanyCoupons());

        System.out.println("Updating coupon #3");
        Coupon coupon = couponRepo.findById(3).get();
        coupon.setTitle("BLAH");
        company2.updateCoupon(Coupon.builder()
                .id(3)
//                .company(company2.getCompanyDetails())
                .category(categoryRepo.getById(1))
                .title("Food Discount")
                .description("bla bla")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now()))
                .amount(10)
                .price(39.90)
                .image("Food")
                .build());

        TablePrinter.print(company2.getCompanyCoupons());

        System.out.println("Print all company coupons by category - FOOD");
        TablePrinter.print(company2.getCompanyCoupons(Categories.FOOD));

        System.out.println("Print all company coupons by max price - 40");
        TablePrinter.print(company2.getCompanyCoupons(40));

        System.out.println("Deleting coupon #3");
        company2.deleteCoupon(3);
        TablePrinter.print(company2.getCompanyCoupons());

        System.out.println("Get company #2 details");
//        TablePrinter.print(company2.getCompanyDetails()); doesn't work - failed to lazily initialize
        System.out.println(company2.getCompanyDetails() + "\n");
    }

}
