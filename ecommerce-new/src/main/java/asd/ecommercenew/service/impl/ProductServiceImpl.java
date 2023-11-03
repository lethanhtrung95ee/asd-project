package asd.ecommercenew.service.impl;

import asd.ecommercenew.dto.request.OrderLineRequest;
import asd.ecommercenew.dto.request.ProductDtoRequest;
import asd.ecommercenew.dto.response.PagingDto;
import asd.ecommercenew.dto.response.ProductDtoResponse;
import asd.ecommercenew.entity.*;
import asd.ecommercenew.repository.*;
import asd.ecommercenew.security.SecurityConfiguration;
import asd.ecommercenew.service.AmazonService;
import asd.ecommercenew.service.ProductService;
import asd.ecommercenew.util.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final SecurityConfiguration securityConfiguration;
    private final AmazonService amazonService;

    @Override
    public void save(ProductDtoRequest productDtoRequest) {
        Product product = modelMapper.map(productDtoRequest, Product.class);
        List<Image> images = new ArrayList<>();
        if (productDtoRequest.getImages() != null) {
            productDtoRequest.getImages().forEach(file -> {
                Image image = new Image();
                image.setUrl(amazonService.uploadFile(file.getBase64()));
                images.add(image);
            });
        }
        product.setImages(images);
        Product product1 = productRepository.save(product);
        images.forEach(image -> {
            image.setProduct(product1);
            imageRepository.save(image);
        });
    }

    @Override
    public PagingDto<ProductDtoResponse> searchProductName(String search, Pageable pageable) {
        return parse(productRepository.findAllByProductNameContaining(search, pageable));
    }

    @Override
    public PagingDto<ProductDtoResponse> findByCategory(String category, Pageable pageable) {
        return parse(productRepository.findAllByCategories(category, pageable));
    }

    @Override
    public ProductDtoResponse findById(int productId) {
        return modelMapper.map(productRepository.findProductById(productId), ProductDtoResponse.class);
    }

    @Override
    public void addToCart(OrderLineRequest orderLineRequest) {
        MyUserDetails myUserDetails = securityConfiguration.getCurrentUser();
        //test junit
        if(myUserDetails == null) {
            User user = new User();
            user.setUsername("user");
            myUserDetails = new MyUserDetails(user);
        }
        Order userOrder = orderRepository.findByUserName(myUserDetails.getUsername());
        User user = userRepository.findByUsername(myUserDetails.getUsername());
        //test junit
        if(userOrder == null && user ==null){
            user = new User();
            user.setUsername("user");
            userOrder = new Order();
            userOrder.setUser(user);
            orderRepository.save(userOrder);
            user.setOrder(userOrder);
            userRepository.save(user);
        }
        else if (userOrder == null) {
            userOrder = new Order();
            userOrder.setUser(user);
            orderRepository.save(userOrder);
            user.setOrder(userOrder);
            userRepository.save(user);
        }
        // Find the product
        Product product = productRepository.findById(orderLineRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create an OrderLine
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(orderLineRequest.getQuantity());
        orderLine.setOrder(userOrder);

        // Save the OrderLine to the database
        orderLineRepository.save(orderLine);
    }

    @Override
    public List<String> getAllCategories() {
        return productRepository.findDistinctCategories();
    }

    public PagingDto<ProductDtoResponse> parse(Page<Product> data) {
        PagingDto<ProductDtoResponse> response = new PagingDto<>();
        List<ProductDtoResponse> dataDto = new ArrayList<>();
        data.getContent().forEach(item -> {
            dataDto.add(modelMapper.map(item, ProductDtoResponse.class));
        });
        response.setContent(dataDto);
        response.setCurrentPage(data.getNumber());
        response.setTotalElements(data.getTotalElements());
        response.setTotalPages(data.getTotalPages());
        return response;
    }
}