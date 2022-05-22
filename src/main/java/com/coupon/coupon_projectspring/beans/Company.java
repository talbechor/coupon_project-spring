package com.coupon.coupon_projectspring.beans;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false
            , length = 25, unique = true)
    private String name;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false
            , length = 25)
    private String password;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
    @JsonIgnore
    private List<Coupon> coupons = new ArrayList<>();


}
