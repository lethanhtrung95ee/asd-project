package asd.ecommercenew.service;


import asd.ecommercenew.dto.request.UserDtoRequest;
import asd.ecommercenew.dto.response.OrderDtoResponse;
import asd.ecommercenew.dto.response.UserDtoResponse;

import java.util.List;

public interface UserService {

    void saveUser(UserDtoRequest userDtoRequest);

    UserDtoResponse findUserByUserName(String userName);

    List<UserDtoResponse> findAll();

    OrderDtoResponse userCart();

    void deleteByUserName(String userName);
}
