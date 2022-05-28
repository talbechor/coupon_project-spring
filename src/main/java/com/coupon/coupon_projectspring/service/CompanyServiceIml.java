package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Coupon;

import com.coupon.coupon_projectspring.exceptions.ExceptionType;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.serviceDAO.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceIml extends ClientService implements CompanyService {
    private int companyID;

    /**
     * Performs a login check for a company
     *
     * @param email    Login email
     * @param password Login password
     * @return boolean that determines if the login was successful, while updating the local variable companyID
     */
    @Override
    public boolean login(String email, String password) throws NotExistsException {
        Optional<Company> companyOptional = companyRepository.findByEmailAndPassword(email, password);
        if (companyOptional.isPresent()) {
            this.companyID = companyOptional.get().getId();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a coupon to the database by a company
     *
     * @param coupon Full coupon instance that will be added to the database by the company logged in
     */
    @Override
    public void addCoupon(Coupon coupon) {
        coupon.setCompany(getCompanyDetails());
        couponRepository.save(coupon);

    }
    /**
     * Updates a coupon in the database by a company
     *
     * @param coupon Full coupon instance that will be updated in the database by the company logged in
     * @throws NotExistsException If the coupon does not exist or if the coupon does not belong to this company,
     *                           Or if the company was not found
     */
    @Override
    public void updateCoupon(Coupon coupon) throws NotExistsException {
        coupon.setCompany(getCompanyDetails());
        if (couponRepository.findByCompany_idAndId(this.companyID, coupon.getId()).isPresent()) {
            couponRepository.save(coupon);
        } else {
            throw new NotExistsException(ExceptionType.COUPON);
        }
    }
    /**
     * Deletes a coupon from the database by a company
     *
     * @param couponID The ID of the company that will be deleted
     * @throws NotExistsException If the coupon does not exist or if the coupon does not belong to this company
     */
    @Override
    public void deleteCoupon(int couponID) throws NotExistsException {
        if (couponRepository.findByCompany_idAndId(this.companyID, couponID).isPresent()) {
            couponRepository.deleteCouponPurchaseByCouponID(couponID);
            couponRepository.deleteById(couponID);
        } else {
            throw new NotExistsException(ExceptionType.COUPON);
        }
    }
    /**
     * Gets all coupons of this company from the database
     *
     * @return A list of all the coupons of this company that exist in the database
     * @throws NotExistsException If no coupons of this company exists in the database
     */
    @Override
    public List<Coupon> getCompanyCoupons() throws NotExistsException {
        List<Coupon> coupons = couponRepository.findAllByCompany_id(this.companyID);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        return coupons;
    }
    /**
     * Gets all coupons of this company and category from the database
     *
     * @param category The category of the coupons which will be retrieved from the database
     * @return A list of all the coupons of this company in the given category that exist in the database
     * @throws NotExistsException If no coupons of this company and category exists in the database
     */
    @Override
    public List<Coupon> getCompanyCoupons(Categories category) throws NotExistsException {
        List<Coupon> coupons = couponRepository.findAllByCompany_idAndCategory_id(this.companyID,category.getValue());
        if (coupons.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        return coupons;
    }
    /**
     * Gets all coupons of this company and price limit from the database
     *
     * @param maxPrice The max price of the coupons which will be retrieved from the database
     * @return A list of all the coupons of this company in the given price limit that exist in the database
     * @throws NotExistsException If no coupons of this company and price limit exists in the database
     */
    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) throws NotExistsException {
        List<Coupon> coupons = couponRepository.findAllByPriceLessThanEqualAndCompany_id(maxPrice, companyID);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ExceptionType.COUPON);
        }
        return coupons;
    }
    /**
     * Gets the company's details from the database
     *
     * @return The details of this company, that will be retrieved from the database
     */
    @Override
    public Company getCompanyDetails() {
        return companyRepository.findById(this.companyID).get();
    }
}
