package com.coupon.coupon_projectspring.service.serviceDAO;

import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface AdminService {
    boolean login(String email, String password);

    void addCompany(Company company) throws AlreadyExistsException;

    void updateCompany(Company company) throws NotExistsException, AlreadyExistsException, SQLIntegrityConstraintViolationException;

    void deleteCompany(int companyID) throws NotExistsException;

    List<Company> getAllCompanies() throws NotExistsException;

    Company getOneCompany(int companyID) throws NotExistsException;

    void addCustomer(Customer customer) throws AlreadyExistsException;

    void updateCustomer(Customer customer) throws NotExistsException, AlreadyExistsException, SQLIntegrityConstraintViolationException;

    void deleteCustomer(int customerId) throws NotExistsException;

    List<Customer> getAllCustomers() throws NotExistsException;

    Customer getOneCustomer(int customerId) throws NotExistsException;
}
