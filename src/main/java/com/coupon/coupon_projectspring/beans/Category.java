package com.coupon.coupon_projectspring.beans;

import lombok.*;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,length = 40,name = "name")
    @Enumerated(EnumType.STRING)
    private Categories categoryName;
}
