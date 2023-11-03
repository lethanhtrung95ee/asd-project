package asd.ecommercenew.repository;

import asd.ecommercenew.dto.response.UserDtoResponse;
import asd.ecommercenew.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String userName);

    void deleteByUsername(String userName);
}
