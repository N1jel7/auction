package by.n1jel.auction.service;

import by.n1jel.auction.dto.AuthentificationResponseDto;
import by.n1jel.auction.dto.LoginRequestDto;
import by.n1jel.auction.dto.RegistrationRequestDto;
import by.n1jel.auction.dto.RegistrationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthentificationService {
    ResponseEntity<RegistrationResponseDto> register(RegistrationRequestDto request);

    AuthentificationResponseDto authenticate(LoginRequestDto request);

    ResponseEntity<AuthentificationResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
