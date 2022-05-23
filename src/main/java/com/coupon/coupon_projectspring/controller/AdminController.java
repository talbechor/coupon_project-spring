package com.coupon.coupon_projectspring.controller;

import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.Company;
import com.coupon.coupon_projectspring.beans.Customer;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.AlreadyExistsException;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.exceptions.TokenException;
import com.coupon.coupon_projectspring.service.LoginManager;
import com.coupon.coupon_projectspring.service.AdminServiceIml;
import com.coupon.coupon_projectspring.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AdminController {
    private final AdminServiceIml adminService;
    private final LoginManager loginManager;
    private final JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws NotExistsException, LoginException {
        userDetails.setClientType(ClientType.ADMIN);
        AdminServiceIml adminService = (AdminServiceIml) loginManager.login(userDetails);
        return new ResponseEntity<>(jwtUtils.generateToken(userDetails), HttpStatus.OK);

    }

    @PostMapping("/addCompany")
    @CrossOrigin
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws AlreadyExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        adminService.addCompany(company);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();

    }

    @PutMapping("/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        adminService.updateCompany(company);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();


    }

    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        adminService.deleteCompany(id);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();


    }

    @GetMapping("/allCompanies")
    @CrossOrigin
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws NotExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(adminService.getAllCompanies());


    }

    @GetMapping("/oneCompany/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws NotExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(adminService.getOneCompany(id));


    }

    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws AlreadyExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        adminService.addCustomer(customer);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();


    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws NotExistsException, SQLIntegrityConstraintViolationException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        adminService.updateCustomer(customer);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token)).build();


    }

    @DeleteMapping("/deleteCustomer{id}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws NotExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        adminService.deleteCustomer(id);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .build();

    }

    @GetMapping("/allCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws NotExistsException, TokenException, LoginException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(adminService.getAllCustomers());


    }

    @GetMapping("/getOneCustomer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws NotExistsException, LoginException, TokenException {
        jwtUtils.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", jwtUtils.generateToken(token))
                .body(adminService.getOneCustomer(id));


    }
}
