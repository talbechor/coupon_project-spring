package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.ExceptionType;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.serviceDAO.AdminService;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class AdminServiceIml extends ClientService implements AdminService {
    /**
     * Performs a login check for admin
     *
     * @param email    Login email
     * @param password Login password
     * @return boolean that determines if the login was successful
     */
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }
    /**
     * Adds a company to the database
     *
     * @param company Full company instance that will be added to the database
     */
    @Override
    public void addCompany(Company company)  {
            companyRepository.save(company);
    }
    /**
     * Updates a company in the database
     *
     * @param company Full company instance that updated be updated in the database
     * @throws NotExistsException If the company's ID and name does not exist in the database
     */
    @Override
    public void updateCompany(Company company) throws NotExistsException {
        if (companyRepository.findByNameAndId(company.getName(), company.getId()).isPresent()) {
            companyRepository.save(company);
        } else {
            throw new NotExistsException(ExceptionType.COMPANY);
        }

    }
    /**
     * Deletes a company from the database
     *
     * @param companyID The ID of the company that will be deleted
     * @throws NotExistsException If the company's ID does not exist in the database
     */
    @Override
    public void deleteCompany(int companyID) throws NotExistsException {

        if (companyRepository.existsById(companyID)) {
            couponRepository.deleteCompanyCoupons(companyID);
            companyRepository.deleteById(companyID);
        } else {
            throw new NotExistsException(ExceptionType.COMPANY);
        }

    }
    /**
     * Gets all companies from the database
     *
     * @return A list of all the companies that exist in the database
     * @throws NotExistsException If no companies exist in the database
     */
    @Override
    public List<Company> getAllCompanies() throws NotExistsException {
        List<Company> companies = companyRepository.findAll();
        if (companies.isEmpty()) {
            throw new NotExistsException(ExceptionType.COMPANY);
        }
        return companies;

    }
    /**
     * Gets a single company from the database by ID
     *
     * @param companyID The ID of the company that will be retrieved from the database
     * @return The details of the company that will be retrieved from the database
     * @throws NotExistsException If the company's ID does not exist in the database
     */
    @Override
    public Company getOneCompany(int companyID) throws NotExistsException {
        return companyRepository.findById(companyID)
                .orElseThrow(() -> new NotExistsException(ExceptionType.COMPANY));
    }
    /**
     * Adds a customer to the database
     *
     * @param customer Full customer instance that will be added to the database
     */
    @Override
    public void addCustomer(Customer customer)  {
        customerRepository.save(customer);
    }
    /**
     * Updates a customer in the database
     *
     * @param customer Full customer instance that will be updated in the database
     * @throws NotExistsException If the customer's ID does not exist in the database
     */
    @Override
    public void updateCustomer(Customer customer) throws NotExistsException  {
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);
        } else {
            throw new NotExistsException(ExceptionType.CUSTOMER);
        }
    }
    /**
     * Deletes a customer from the database
     *
     * @param customerId The ID of the customer that will be deleted
     * @throws NotExistsException If the customer's ID does not exist in the database
     */
    @Override
    public void deleteCustomer(int customerId) throws NotExistsException {
        if (customerRepository.existsById(customerId)) {
            couponRepository.deleteCouponPurchaseByCustomerID(customerId);
            customerRepository.deleteById(customerId);
        } else {
            throw new NotExistsException(ExceptionType.CUSTOMER);
        }

    }
    /**
     * Gets all customers from the database
     *
     * @return A list of all the customers that exist in the database
     * @throws NotExistsException If no customers exist in the database
     */
    @Override
    public List<Customer> getAllCustomers() throws NotExistsException {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new NotExistsException(ExceptionType.CUSTOMER);
        }
        return customers;
    }
    /**
     * Gets a single customer from the database by ID
     *
     * @param customerId The ID of the customer that will be retrieved from the database
     * @return The details of the customer that will be retrieved from the database
     * @throws NotExistsException If the customer's ID does not exist in the database
     */
    @Override
    public Customer getOneCustomer(int customerId) throws NotExistsException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotExistsException(ExceptionType.CUSTOMER));
    }
}
