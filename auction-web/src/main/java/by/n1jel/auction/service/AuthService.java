package by.n1jel.auction.service;

import by.n1jel.auction.dto.*;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    RegistrationResponse register(RegistrationRequest registrationRequest);
}
