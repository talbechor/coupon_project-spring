package com.coupon.coupon_projectspring.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Component
public class UserDetails {
    private String email;
    private String password;
    private ClientType clientType;
}
