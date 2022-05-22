package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.serviceDAO.AdminService;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class AdminServiceIml extends ClientService implements AdminService {
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    @Override
    public void addCompany(Company company)  {
            companyRepository.save(company);
    }

    @Override
    public void updateCompany(Company company) throws NotExistsException {
        if (companyRepository.findByNameAndId(company.getName(), company.getId()).isPresent()) {
            companyRepository.save(company);
        } else {
            throw new NotExistsException(ClientType.COMPANY);
        }

    }

    @Override
    public void deleteCompany(int companyID) throws NotExistsException {

        if (companyRepository.existsById(companyID)) {
            couponRepository.deleteCouponsFromTableCustomer_vs_CouponByCompanyId(companyID);
            companyRepository.deleteById(companyID);
        } else {
            throw new NotExistsException(ClientType.COMPANY);
        }

    }

    @Override
    public List<Company> getAllCompanies() throws NotExistsException {
        List<Company> companies = companyRepository.findAll();
        if (companies.isEmpty()) {
            throw new NotExistsException(ClientType.COMPANY);
        }
        return companies;

    }

    @Override
    public Company getOneCompany(int companyID) throws NotExistsException {
        return companyRepository.findById(companyID)
                .orElseThrow(() -> new NotExistsException(ClientType.COMPANY));
    }

    @Override
    public void addCustomer(Customer customer)  {
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws NotExistsException  {
        if (customerRepository.existsById(customer.getId())) {
            customerRepository.save(customer);
        } else {
            throw new NotExistsException(ClientType.CUSTOMER);
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws NotExistsException {
        if (customerRepository.existsById(customerId)) {
            couponRepository.deleteCustomerFromTableCustomer_vs_Coupon(customerId);
            customerRepository.deleteById(customerId);
        } else {
            throw new NotExistsException(ClientType.CUSTOMER);
        }

    }

    @Override
    public List<Customer> getAllCustomers() throws NotExistsException {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new NotExistsException(ClientType.CUSTOMER);
        }
        return customers;
    }

    @Override
    public Customer getOneCustomer(int customerId) throws NotExistsException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotExistsException(ClientType.CUSTOMER));
    }
}
