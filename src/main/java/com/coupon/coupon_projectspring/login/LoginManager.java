package com.coupon.coupon_projectspring.login;

import com.coupon.coupon_projectspring.beans.ClientType;
import com.coupon.coupon_projectspring.beans.UserDetails;
import com.coupon.coupon_projectspring.exceptions.LoginException;
import com.coupon.coupon_projectspring.exceptions.NotExistsException;
import com.coupon.coupon_projectspring.service.AdminServiceIml;
import com.coupon.coupon_projectspring.service.ClientService;
import com.coupon.coupon_projectspring.service.CompanyServiceIml;
import com.coupon.coupon_projectspring.service.CustomerServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    @Autowired
    private AdminServiceIml adminService;
    @Autowired
    private CompanyServiceIml companyService;
    @Autowired
    private CustomerServiceIml customerService;

    public ClientService login(UserDetails userDetails) throws NotExistsException, LoginException {
        ClientService clientFacade = null;
        switch (userDetails.getClientType()) {
            case ADMIN:
                clientFacade = adminService;
                break;

            case COMPANY:
                clientFacade = companyService;
                break;
            case CUSTOMER:
                clientFacade = customerService;
                break;
            default: throw new LoginException();

        }

        assert clientFacade != null;
        if (clientFacade.login(userDetails.getEmail(), userDetails.getPassword())) {
            return clientFacade;
        } else {
            throw new LoginException(userDetails.getClientType());
        }

    }
    /*
    ClientService clientFacade = switch (clientType) {
            case ADMIN -> adminService;
            case COMPANY -> companyService;
            case CUSTOMER -> customerService;
            default -> null;
        };
     */
}
