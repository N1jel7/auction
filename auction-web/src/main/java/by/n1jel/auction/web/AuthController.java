package by.n1jel.auction.web;

import by.n1jel.auction.dto.*;
import by.n1jel.auction.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lots")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public AuthResponse authorization(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public RegistrationResponse registration(@RequestBody RegistrationRequest regRequest) {
        return authService.register(regRequest);
    }
}
