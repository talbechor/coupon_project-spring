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
    @Column(unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Categories title;
}
