package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Address;
import asd.ecommercenew.entity.Order;
import asd.ecommercenew.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT o FROM Order o JOIN o.user u WHERE u.username = :username")
    Order findByUserName(@Param("username") String username);

}
