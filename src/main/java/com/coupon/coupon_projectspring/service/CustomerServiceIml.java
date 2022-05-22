package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.serviceDAO.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceIml extends ClientService implements CustomerService {
    private int customerID;
    @Override
    public boolean login(String email, String password) throws NotExistsException {
        Optional<Customer> customerOptional = customerRepository.findByEmailAndPassword(email, password);
        if (customerOptional.isPresent()) {
            this.customerID = customerOptional.get().getId();
            return true;
        } else {
            throw new NotExistsException(ClientType.CUSTOMER);
        }
    }

    @Override
    public void purchaseCoupon(Coupon coupon) throws AlreadyExistsException, NotExistsException {

        if (couponRepository.countCouponPurchase(customerID, coupon.getId()) > 0) {
            throw new AlreadyExistsException(ClientType.COUPON);
        }
        if (!couponRepository.existsById(coupon.getId())) {
            throw new NotExistsException(ClientType.COUPON);
        }
        if (couponRepository.countCouponAmountById(coupon.getId()) == 0) {
            throw new NotExistsException(ClientType.COUPON);
        }
        if (couponRepository.countCouponExpiredById(coupon.getId()) == 0) {
            throw new NotExistsException(ClientType.COUPON);
        }
        couponRepository.addCouponPurchaseCustomer(this.customerID, coupon.getId());
        couponRepository.updateCouponAmount(coupon.getId());


    }

    @Override
    public List<Coupon> getCustomerCoupons() throws NotExistsException {
        List<Coupon> coupons = couponRepository.getCouponsCustomer(this.customerID);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ClientType.COUPON);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsByCategory(Categories category) throws NotExistsException {
        List<Coupon> coupons = couponRepository.getCouponCustomerByCategory(this.customerID, category.VALUE);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ClientType.COUPON);
        }
        return coupons;

    }

    @Override
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws NotExistsException {
        List<Coupon> coupons = couponRepository.getCouponsPrice(this.customerID, maxPrice);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ClientType.COUPON);
        }
        return coupons;
    }

    @Override
    public Customer getCustomerDetails() {
        return customerRepository.findById(this.customerID).get();
    }
}
