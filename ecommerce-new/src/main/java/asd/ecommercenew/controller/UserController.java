package asd.ecommercenew.controller;

import asd.ecommercenew.dto.request.UserDtoRequest;
import asd.ecommercenew.dto.response.OrderDtoResponse;
import asd.ecommercenew.dto.response.UserDtoResponse;
import asd.ecommercenew.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    //for testing or admin can look all user in db
    @GetMapping
    ResponseEntity<List<UserDtoResponse>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    ResponseEntity<Optional<UserDtoResponse>> getProfile(@PathVariable String username) {
        return new ResponseEntity<>(Optional.ofNullable(userService.findUserByUserName(username)), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<String> save(@RequestBody UserDtoRequest userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>("User save successfully", HttpStatus.OK);
    }

    @GetMapping("/cart")
    ResponseEntity<OrderDtoResponse> getUserCart() {
        return new ResponseEntity<>(userService.userCart(), HttpStatus.OK);
    }

}
