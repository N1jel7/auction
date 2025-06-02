package by.n1jel.auction.service;

import by.n1jel.auction.dto.AuthentificationResponseDto;
import by.n1jel.auction.dto.LoginRequestDto;
import by.n1jel.auction.dto.RegistrationRequestDto;
import by.n1jel.auction.dto.RegistrationResponseDto;
import by.n1jel.auction.entity.Token;
import by.n1jel.auction.entity.User;
import by.n1jel.auction.enums.Role;
import by.n1jel.auction.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseEntity<RegistrationResponseDto> register(RegistrationRequestDto request) {
        List<String> errors = new ArrayList<>();
        if (userService.existsByUsername(request.username())) {
            errors.add("Username already taken");
        }

        if (userService.existsByEmail(request.email())) {
            errors.add("Email already taken");
        }

        if (!errors.isEmpty()) {
            return new ResponseEntity<>(new RegistrationResponseDto(400, "Fail", errors.toString()), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user = userService.saveUser(user);

        return new ResponseEntity<>(new RegistrationResponseDto(200, "Success", "You successfully registered!"), HttpStatus.OK);
    }

    private void revokeAllToken(User user) {

        List<Token> validTokens = tokenRepository.findAllAccessTokenByUser(user.getId());

        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {

        Token token = new Token();

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);

        tokenRepository.save(token);
    }

    public AuthentificationResponseDto authenticate(LoginRequestDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        User user = userService.findUserByUsername(request.username());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthentificationResponseDto(accessToken, refreshToken);
    }

    public ResponseEntity<AuthentificationResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userService.findUserByUsername(username);

        if (jwtService.isValidRefresh(token, user)) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity<>(new AuthentificationResponseDto(accessToken, refreshToken), HttpStatus.OK);

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
