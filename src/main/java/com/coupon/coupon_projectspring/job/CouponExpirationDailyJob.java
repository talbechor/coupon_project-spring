package com.coupon.coupon_projectspring.job;


import com.coupon.coupon_projectspring.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class CouponExpirationDailyJob {
    private final CouponRepository couponRepository;


    @Scheduled(cron = "0 0 0 * * ? ", zone = "Asia/Jerusalem")
    public void eraseCoupon(){

        couponRepository.deletePurchasedExpiredCoupons();
        couponRepository.deleteExpiredCoupons();
        System.out.println("delete Coupon");

    }
}
