package asd.ecommercenew.service.impl;

import asd.ecommercenew.dto.request.UserDtoRequest;
import asd.ecommercenew.dto.response.OrderDtoResponse;
import asd.ecommercenew.dto.response.UserDtoResponse;
import asd.ecommercenew.entity.Address;
import asd.ecommercenew.entity.Order;
import asd.ecommercenew.entity.User;
import asd.ecommercenew.repository.AddressRepository;
import asd.ecommercenew.repository.UserRepository;
import asd.ecommercenew.security.SecurityConfiguration;
import asd.ecommercenew.service.UserService;
import asd.ecommercenew.util.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableCaching
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;
    private final AddressRepository addressRepository;

    @Override
    public void saveUser(UserDtoRequest userDtoRequest) {
        User user = new User();
        user.setEmail(userDtoRequest.getEmail());
        user.setUsername(userDtoRequest.getUsername());
        user.setFirstName(userDtoRequest.getFirstName());
        user.setLastName(userDtoRequest.getLastName());
        user.setActive(true);
        Address address = new Address();
        address.setFullAddress(userDtoRequest.getAddressDtoRequest().getFullAddress());
//        address.setUser(user);
//        Address address1 = addressRepository.save(address);
        user.setAddress(address);
        userRepository.save(modelMapper.map(user, User.class));
    }

    @Override
    @Cacheable(value = "users", key = "#userName", unless = "#result == null")
    public UserDtoResponse findUserByUserName(String userName) {
        return modelMapper.map(userRepository.findByUsername(userName), UserDtoResponse.class);
    }

    @Override
    public List<UserDtoResponse> findAll() {
        var result = new ArrayList<UserDtoResponse>();
        userRepository.findAll().forEach(item -> {
            result.add(modelMapper.map(item, UserDtoResponse.class));
        });
        return result;
    }

    @Override
    public OrderDtoResponse userCart() {
        MyUserDetails myUserDetails = securityConfiguration.getCurrentUser();
        User user = userRepository.findByUsername(myUserDetails.getUsername());
        Order detail = user.getOrder() == null ? new Order() : user.getOrder();
        return modelMapper.map(detail, OrderDtoResponse.class);
    }

    @Override
    @CacheEvict(value = "users", key = "#username")
    public void deleteByUserName(String userName) {
        userRepository.deleteByUsername(userName);
    }
}
