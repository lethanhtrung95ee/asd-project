package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Address;
import asd.ecommercenew.entity.PaymentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentType, Integer> {
}
