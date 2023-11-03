package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Address;
import asd.ecommercenew.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
