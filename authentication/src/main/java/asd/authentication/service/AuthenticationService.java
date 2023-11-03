package asd.authentication.service;

import asd.authentication.dto.LoginDTO;
import asd.authentication.dto.LoginResponse;
import asd.authentication.dto.LoginResponseDTO;

public interface AuthenticationService {

    LoginResponseDTO authenticate(LoginDTO loginDto);
}