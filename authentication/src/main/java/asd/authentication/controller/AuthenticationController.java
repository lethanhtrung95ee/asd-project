package asd.authentication.controller;

import asd.authentication.dto.LoginResponseDTO;
import asd.authentication.service.AuthenticationService;
import asd.authentication.dto.LoginDTO;
import asd.authentication.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDto) {
        return authenticationService.authenticate(loginDto);
    }

}