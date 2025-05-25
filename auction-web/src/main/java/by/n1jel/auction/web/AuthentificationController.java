package by.n1jel.auction.web;

import by.n1jel.auction.dto.*;
import by.n1jel.auction.service.AuthentificationService;
import by.n1jel.auction.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lots")
public class AuthentificationController {

    private final AuthentificationService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthentificationResponseDto> authorization(@RequestBody @Validated LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.authenticate(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registration(@RequestBody @Validated RegistrationRequestDto regRequest) {
        return authService.register(regRequest);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthentificationResponseDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authService.refreshToken(request, response);
    }
}
