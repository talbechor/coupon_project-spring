package com.coupon.coupon_projectspring.service;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Coupon;

import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.serviceDAO.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceIml extends ClientService implements CompanyService {
    private int companyID;
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

    @Override
    public void addCoupon(Coupon coupon)  {
        coupon.setCompany(getCompanyDetails());
        couponRepository.save(coupon);

    }

    @Override
    public void updateCoupon(Coupon coupon) throws NotExistsException {
        coupon.setCompany(getCompanyDetails());
        if (couponRepository.findByCompany_idAndId(this.companyID, coupon.getId()).isPresent()) {
            couponRepository.save(coupon);
        } else {
            throw new NotExistsException(ClientType.COUPON);
        }
    }

    @Override
    public void deleteCoupon(int couponID) throws NotExistsException {
        if (couponRepository.findByCompany_idAndId(this.companyID, couponID).isPresent()) {
            couponRepository.deleteCouponFromTableCustomer_vs_Coupon(couponID);
            couponRepository.deleteById(couponID);
        } else {
            throw new NotExistsException(ClientType.COUPON);
        }
    }

    @Override
    public List<Coupon> getCompanyCoupons() throws NotExistsException {
        List<Coupon> coupons = couponRepository.findAllByCompany_id(this.companyID);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ClientType.COUPON);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCompanyCoupons(Categories category) throws NotExistsException {
        List<Coupon> coupons = couponRepository.findAllByCompany_idAndCategory_id(this.companyID, category.VALUE);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ClientType.COUPON);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) throws NotExistsException {
        List<Coupon> coupons = couponRepository.findAllByPriceLessThanEqualAndCompany_id(maxPrice,companyID);
        if (coupons.isEmpty()) {
            throw new NotExistsException(ClientType.COUPON);
        }
        return coupons;
    }

    @Override
    public Company getCompanyDetails() {
        return companyRepository.findById(this.companyID).get();
    }
}
