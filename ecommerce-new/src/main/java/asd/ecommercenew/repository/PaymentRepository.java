package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    @Query("SELECT p " +
            "FROM Payment p " +
            "WHERE p.user.id = :id")
    List<Payment> findAllByUser(int id);
}
