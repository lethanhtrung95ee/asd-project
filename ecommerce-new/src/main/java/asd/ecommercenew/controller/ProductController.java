package asd.ecommercenew.controller;

import asd.ecommercenew.dto.request.OrderLineRequest;
import asd.ecommercenew.dto.request.ProductDtoRequest;
import asd.ecommercenew.dto.response.PagingDto;
import asd.ecommercenew.dto.response.ProductDtoResponse;
import asd.ecommercenew.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping("/searchProductName")
    ResponseEntity<PagingDto<ProductDtoResponse>> searchProductName(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size,
                                                                    @RequestParam(required = false) String productName) {
        return new ResponseEntity<>(productService.searchProductName(productName, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/searchCategory")
    ResponseEntity<PagingDto<ProductDtoResponse>> findByCategory(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size,
                                                                 @RequestParam(required = false) String category) {
        return new ResponseEntity<>(productService.findByCategory(category, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDtoResponse> findById(@PathVariable int productId) {
        return new ResponseEntity<>(productService.findById(productId), HttpStatus.OK);
    }

    @PostMapping("/addToCart")
    ResponseEntity<String> addToCart(@RequestBody OrderLineRequest orderLine) {
        productService.addToCart(orderLine);
        return new ResponseEntity<>("Product added to the cart successfully", HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<String> save(@RequestBody ProductDtoRequest productDtoRequest) {
        productService.save(productDtoRequest);
        return new ResponseEntity<>("Product save successfully", HttpStatus.OK);
    }

    @GetMapping("/categories")
    ResponseEntity<List<String>> getAllCategories() {
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }
}
