package com.coupon.coupon_projectspring.controller;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.LoginManager;
import com.coupon.coupon_projectspring.service.AdminServiceIml;
import com.coupon.coupon_projectspring.service.CompanyServiceIml;
import com.coupon.coupon_projectspring.service.CustomerServiceIml;
import com.coupon.coupon_projectspring.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class LoginController {
    private final LoginManager loginManager;
    private final AdminServiceIml adminService;
    private final CompanyServiceIml companyService;
    private final CustomerServiceIml customerService;
    private final JWTUtils jwtUtils;


    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> userLogin(@RequestBody UserDetails userDetails) throws NotExistsException, LoginException {
        if (loginManager.login(userDetails)!= null) {
            return ResponseEntity.ok()
                    .header(jwtUtils.generateToken(userDetails)).build();
            //return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);
        } else {
            throw new LoginException();
        }

    }
}
