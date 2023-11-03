package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Address;
import asd.ecommercenew.entity.OrderLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLine, Integer> {
    List<OrderLine> findAllByPaymentId(int paymentId);
}
