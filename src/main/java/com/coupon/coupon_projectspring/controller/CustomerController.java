package com.coupon.coupon_projectspring.controller;

import com.coupon.coupon_projectspring.beans.Categories;
import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Coupon;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.exceptions.TokenException;
import com.coupon.coupon_projectspring.login.LoginManager;
import com.coupon.coupon_projectspring.service.CustomerServiceIml;
import com.coupon.coupon_projectspring.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceIml customerService;
    private final LoginManager loginManager;
    private final JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws NotExistsException, LoginException {
        userDetails.setClientType(ClientType.CUSTOMER);
        CustomerServiceIml customerService = (CustomerServiceIml) loginManager.login(userDetails);
        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);
    }


    @PostMapping("/purchaseCoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws AlreadyExistsException, NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.CUSTOMER);
        customerService.purchaseCoupon(coupon);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.checkUser(token))
                .build();
    }

    @GetMapping("/customerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) throws LoginException, NotExistsException, TokenException {
        jwtUtils.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.checkUser(token))
                .body(customerService.getCustomerCoupons());


    }

    @GetMapping("/customerCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name = "Authorization") String token, @PathVariable Categories category) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.CUSTOMER);
        String newToken = jwtUtils.checkUser(token);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.checkUser(token))
                .body(customerService.getCustomerCouponsByCategory(category));

    }

    @GetMapping("/customerCoupons/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws LoginException, NotExistsException, TokenException {
        jwtUtils.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.checkUser(token))
                .body(customerService.getCustomerCouponsByMaxPrice(maxPrice));


    }

    @GetMapping("/customerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.checkUser(token))
                .body(customerService.getCustomerDetails());
    }

}
