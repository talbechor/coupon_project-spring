package com.coupon.coupon_projectspring.controller;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.exceptions.TokenException;
import com.coupon.coupon_projectspring.service.LoginManager;
import com.coupon.coupon_projectspring.service.CompanyServiceIml;
import com.coupon.coupon_projectspring.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyServiceIml companyService;
    private final LoginManager loginManager;
    private final JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> companyLogin(@RequestBody UserDetails userDetails) throws NotExistsException, LoginException {
        userDetails.setClientType(ClientType.COMPANY);
        CompanyServiceIml companyService = (CompanyServiceIml) loginManager.login(userDetails);
        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);

    }


    @PostMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws AlreadyExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        companyService.addCoupon(coupon);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();
    }


    @PutMapping("/updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws NotExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        companyService.updateCoupon(coupon);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();

    }

    @DeleteMapping("/deleteCoupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        companyService.deleteCoupon(id);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();
    }


    @GetMapping("/companyCoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(companyService.getCompanyCoupons());

    }

    @GetMapping("/companyCouponsByCategory/{cat}")
    public ResponseEntity<?> getCompanyCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Categories cat) throws NotExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(companyService.getCompanyCoupons(cat));

    }

    @GetMapping("/companyCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCompanyCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(companyService.getCompanyCoupons(maxPrice));

    }

    @GetMapping("/companyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(companyService.getCompanyDetails());

    }

}
