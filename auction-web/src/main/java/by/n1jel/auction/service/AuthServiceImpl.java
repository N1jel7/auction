package by.n1jel.auction.service;

import by.n1jel.auction.dto.AuthRequest;
import by.n1jel.auction.dto.AuthResponse;
import by.n1jel.auction.dto.RegistrationRequest;
import by.n1jel.auction.dto.RegistrationResponse;
import by.n1jel.auction.jwt.JwtHelper;
import by.n1jel.auction.mapper.UserMapper;
import by.n1jel.auction.model.User;
import by.n1jel.auction.utils.LotIdGenerator;
import by.n1jel.auction.utils.UserConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final LotIdGenerator lotIdGenerator;
    private final UserConfig userConfig;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtHelper.createToken(Collections.emptyMap(), userDetails.getUsername());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        if (userConfig.findByUsername(registrationRequest.username()) == null) {
            User user = userMapper.mapToUser(registrationRequest);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setId(lotIdGenerator.getId());
            userConfig.putUser(user);
            return new RegistrationResponse(user.getId(), user.getUsername());
        } else {
            throw new BadCredentialsException("Username already taken");
        }

    }
}
