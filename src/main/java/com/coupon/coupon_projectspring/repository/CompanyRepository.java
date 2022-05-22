package com.coupon.coupon_projectspring.repository;
import com.coupon.coupon_projectspring.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
    // for login
    Optional<Company> findByEmailAndPassword(String email, String password);

    // update company
    Optional<Company> findByNameAndId( String name,int id);


}
