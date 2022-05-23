package com.coupon.coupon_projectspring.clr;


import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.Category;
import com.coupon.coupon_projectspring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
@RequiredArgsConstructor
@Order(1)
public class TestBeginDB implements CommandLineRunner {

    private final CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("creating Category to DB");
        createCategoryTable();


    }

    private void createCategoryTable() {
        Category category1 = Category.builder()
                .title(Categories.FOOD)
                .build();

        Category category2 = Category.builder()
                .title(Categories.ELECTRICITY)
                .build();
        Category category3 = Category.builder()
                .title(Categories.RESTAURANT)
                .build();
        Category category4 = Category.builder()
                .title(Categories.VACATION)
                .build();
        categoryRepository.saveAll(List.of(category1, category2, category3, category4));

    }


}
