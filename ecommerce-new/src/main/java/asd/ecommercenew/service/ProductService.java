package asd.ecommercenew.service;


import asd.ecommercenew.dto.request.OrderLineRequest;
import asd.ecommercenew.dto.request.ProductDtoRequest;
import asd.ecommercenew.dto.response.PagingDto;
import asd.ecommercenew.dto.response.ProductDtoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    void save(ProductDtoRequest productDtoRequest);

    PagingDto<ProductDtoResponse> searchProductName(String search, Pageable pageable);

    PagingDto<ProductDtoResponse> findByCategory(String category, Pageable pageable);

    ProductDtoResponse findById(int productId);

    void addToCart(OrderLineRequest orderLineRequest);

    List<String> getAllCategories();

}
