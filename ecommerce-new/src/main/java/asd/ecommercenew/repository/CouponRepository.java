package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Address;
import asd.ecommercenew.entity.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {
}
