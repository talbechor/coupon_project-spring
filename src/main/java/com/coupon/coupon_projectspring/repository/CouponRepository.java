package com.coupon.coupon_projectspring.repository;
import com.coupon.coupon_projectspring.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

  

    Optional<Coupon> findByCompany_idAndId(int company_id, int id);

    List<Coupon> findAllByCompany_idAndCategory_id(int company_id, int category);

    List<Coupon> findAllByCompany_id(int company_id);

    List<Coupon> findAllByPriceLessThanEqualAndCompany_id(double maxPrice,int company_id);


    // 10% repo

    // purchase coupon
    @Query(value = "select count(*) from customers_vs_coupons  where customer_id = ?1 and coupons_id in (?2)", nativeQuery = true)
    int countCouponPurchase(int customer_id, int coupon_id);

    // check coupon amount before purchase
    @Query(value = "select count(*) from Coupons  where id = ?1 and amount>0 ", nativeQuery = true)
    int countCouponAmountById(int coupon_id);

    // check coupon expired date before purchase
    @Query(value = "select count(*) from Coupons  where id = ?1 and DATE(end_date)>DATE(now())", nativeQuery = true)
    int countCouponExpiredById(int coupon_id);

    // insert into cvc table after purchase
    @Transactional
    @Modifying
    @Query(value = "insert into customers_vs_coupons (customer_id,coupons_id) values(?1,?2)", nativeQuery = true)
    void addCouponPurchaseCustomer(int customer_id, int coupon_id);

    //  update coupon amount after purchase
    @Transactional
    @Modifying
    @Query(value = "update coupons set amount=amount-1 where id=?1 ", nativeQuery = true)
    void updateCouponAmount(int coupon_id);

    // delete customer purchase history  before we delete customer
    @Transactional
    @Modifying
    @Query(value = "delete from customers_vs_coupons where customer_id=?1 ", nativeQuery = true)
    void deleteCustomerFromTableCustomer_vs_Coupon(int customer_id);

    // delete coupons purchase history before company delete her coupon
    @Transactional
    @Modifying
    @Query(value = "delete from customers_vs_coupons where coupons_id=?1 ", nativeQuery = true)
    void deleteCouponFromTableCustomer_vs_Coupon(int coupon_id);

    // delete all company coupons history purchase from cvc before we delete company
    @Transactional
    @Modifying
    @Query(value = " DELETE FROM customers_vs_coupons WHERE customer_id <>0 and coupons_id=any " +
            " (select id from coupons where company_id=?1 )", nativeQuery = true)
    void deleteCouponsFromTableCustomer_vs_CouponByCompanyId(int company_id);

    // using inner join to read coupons customer
    @Query(value = " SELECT * FROM coupons INNER JOIN customers_vs_coupons ON " +
            "coupons.id =customers_vs_coupons.coupons_id WHERE customers_vs_coupons.customer_id=?1 ", nativeQuery = true)
    List<Coupon> getCouponsCustomer(int customer_id);

    // using inner join to read coupons customer by category
    @Query(value = " SELECT * FROM coupons INNER JOIN customers_vs_coupons ON " +
            "coupons.id =customers_vs_coupons.coupons_id  WHERE customers_vs_coupons.customer_id=?1 " +
            "AND category_id =?2 ", nativeQuery = true)
    List<Coupon> getCouponCustomerByCategory(int customer_id, int category);

    // using inner join to read coupons customer by max price
    @Query(value = " SELECT * FROM coupons INNER JOIN customers_vs_coupons ON coupons.id =customers_vs_coupons.coupons_id WHERE customers_vs_coupons.customer_id=?1 AND Price <= ?2 ", nativeQuery = true)
    List<Coupon> getCouponsPrice(int customer_id, double MaxPrice);

    // thread custom query
    @Transactional
    @Modifying
    @Query(value = " DELETE FROM coupons WHERE id>0 AND end_date< curdate()", nativeQuery = true)
    void deleteExpiredCoupons();

    @Transactional
    @Modifying
    @Query(value = " DELETE FROM customers_vs_coupons WHERE customer_id <>0 and coupons_id=any" +
            " (select id from coupons where end_date< curdate())", nativeQuery = true)
    void deleteExpiredCouponsFromTableCVC();


}
