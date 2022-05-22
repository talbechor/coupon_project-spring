package com.coupon.coupon_projectspring.repository;
import com.coupon.coupon_projectspring.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // login check
    Optional<Customer> findByEmailAndPassword(String email, String password);

    // add customer



}
