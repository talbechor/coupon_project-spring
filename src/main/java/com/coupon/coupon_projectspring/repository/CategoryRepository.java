package com.coupon.coupon_projectspring.repository;

import com.coupon.coupon_projectspring.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
