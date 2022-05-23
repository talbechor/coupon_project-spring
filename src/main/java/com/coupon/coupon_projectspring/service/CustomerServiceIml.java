package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.ExceptionType;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.serviceDAO.CustomerService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceIml extends ClientService implements CustomerService {
    private int customerID;
    /**
     * Performs a login check for a customer
     *
     * @param email    Login email
     * @param password Login password
     * @return boolean that determines if the login was successful, while updating the local variable customerID
     */
    @Override
    public boolean login(String email, String password) throws NotExistsException {
        Optional<Customer> customerOptional = customerRepository.findByEmailAndPassword(email, password);
        if (customerOptional.isPresent()) {
            this.customerID = customerOptional.get().getId();
            return true;
        } else {
            throw new NotExistsException(ExceptionType.CUSTOMER);
        }
    }

    @Override
    public void purchaseCoupon(int couponID ) throws AlreadyExistsException, NotExistsException {

        Optional<Coupon> optionalCoupon = couponRepository.findById(couponID);

        if (optionalCoupon.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        if (couponRepository.countCouponPurchase(this.customerID, couponID) > 0) {
            throw new AlreadyExistsException(ExceptionType.COUPON);
        }
        if (optionalCoupon.get().getAmount() == 0) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        if (optionalCoupon.get().getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        couponRepository.addCouponPurchase(this.customerID, couponID);
        couponRepository.decreaseCouponAmount(couponID);

    }

    @Override
    public List<Coupon> getCustomerCoupons() throws NotExistsException {
        List<Coupon> coupons = couponRepository.getAllCouponsCustomer(this.customerID);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsByCategory(Categories category) throws NotExistsException {
        List<Coupon> coupons = couponRepository.getAllCouponCustomerByCategory(this.customerID, category.VALUE);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        return coupons;

    }

    @Override
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws NotExistsException {
        List<Coupon> coupons = couponRepository.getAllCouponsCustomerByPrice(this.customerID, maxPrice);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        return coupons;
    }

    @Override
    public Customer getCustomerDetails() {
        return customerRepository.findById(this.customerID).get();
    }
}
