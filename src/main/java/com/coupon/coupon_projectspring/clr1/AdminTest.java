package com.coupon.coupon_projectspring.clr1;

import com.coupon.coupon_projectspring.beans.*;
import com.coupon.coupon_projectspring.repository.CategoryRepository;
import com.coupon.coupon_projectspring.service.AdminServiceIml;
import com.coupon.coupon_projectspring.service.LoginManager;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Order(1)
public class AdminTest implements CommandLineRunner {
    private final LoginManager loginService;

    private final CategoryRepository categoryRepo;

    @Override
    public void run(String... args) throws Exception {
        addCategories();

        System.out.println("Admin Test \n");
        AdminServiceIml admin = (AdminServiceIml) loginService.login(
                new UserDetails("admin@admin.com", "admin", ClientType.ADMIN));

        companiesTest(admin);
        customersTest(admin);
    }

    private void addCategories() {
        categoryRepo.save(Category.builder().title(Categories.FOOD).build());
        categoryRepo.save(Category.builder().title(Categories.ELECTRICITY).build());
        categoryRepo.save(Category.builder().title(Categories.RESTAURANT).build());
        categoryRepo.save(Category.builder().title(Categories.VACATION).build());
    }

    private void companiesTest(AdminServiceIml admin) throws Exception {
        admin.addCompany(Company.builder()
                .name("Oren INC")
                .email("no-reply@oren.inc")
                .password("orenInc")
                .build());

        admin.addCompany(Company.builder()
                .name("Lea INC")
                .email("no-reply@lea.inc")
                .password("leasInc")
                .build());

        admin.addCompany(Company.builder()
                .name("Tal INC")
                .email("no-reply@tal.inc")
                .password("talbInc")
                .build());

        System.out.println("Adding companies");
//        TablePrinter.print(admin.getAllCompanies()); doesn't work - failed to lazily initialize
        admin.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("Deleting company");
        admin.deleteCompany(2);
//        TablePrinter.print(admin.getAllCompanies()); doesn't work - failed to lazily initialize
        admin.getAllCompanies().forEach(System.out::println);
        System.out.println();

        System.out.println("Updating company");
        admin.updateCompany(Company.builder()
                .id(3)
                .name("Tal INC")
                .email("tal@gmail.inc")
                .password("taltal")
                .build());

//        TablePrinter.print(admin.getAllCompanies()); doesn't work - failed to lazily initialize
        admin.getAllCompanies().forEach(System.out::println);
        System.out.println();
    }

    private void customersTest(AdminServiceIml admin) throws Exception {
        admin.addCustomer(Customer.builder()
                .firstName("Oren")
                .lastName("Levi")
                .email("orenlevi6@gmail.com")
                .password("orenOren")
                .build());

        admin.addCustomer(Customer.builder()
                .firstName("Lea")
                .lastName("Sad")
                .email("leasad@gmail.com")
                .password("leaLea")
                .build());

        admin.addCustomer(Customer.builder()
                .firstName("Tal")
                .lastName("Bechor")
                .email("talb@gmail.com")
                .password("talTal")
                .build());

        System.out.println("Adding customers");
//        TablePrinter.print(admin.getAllCustomers()); doesn't work - failed to lazily initialize
        admin.getAllCustomers().forEach(System.out::println);
        System.out.println();

        System.out.println("Deleting customer");
        admin.deleteCustomer(2);
//        TablePrinter.print(admin.getAllCustomers()); doesn't work - failed to lazily initialize
        admin.getAllCustomers().forEach(System.out::println);
        System.out.println();

        System.out.println("Updating customer");
        admin.updateCustomer(Customer.builder()
                .id(3)
                .firstName("tal")
                .lastName("Bechor")
                .email("tal@gmail.com")
                .password("taltal")
                .build());

//        TablePrinter.print(admin.getAllCustomers()); doesn't work - failed to lazily initialize
        admin.getAllCustomers().forEach(System.out::println);
        System.out.println();
    }

}
