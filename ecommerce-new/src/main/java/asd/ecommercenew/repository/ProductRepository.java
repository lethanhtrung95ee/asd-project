package asd.ecommercenew.repository;

import asd.ecommercenew.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Page<Product> findAllByCategories(String categoryType, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:searchString%")
    Page<Product> findAllByProductNameContaining(@Param("searchString") String searchString, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT DISTINCT TRIM(p.categories) FROM Product p WHERE p.categories IS NOT NULL")
    List<String> findDistinctCategories();

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findProductById(@Param("id") int id);
}
