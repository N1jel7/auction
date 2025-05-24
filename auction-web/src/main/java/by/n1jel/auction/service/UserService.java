package by.n1jel.auction.service;

import by.n1jel.auction.utils.UserConfig;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserConfig userConfig;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username) || userConfig.findByUsername(username) == null) {
            throw new UsernameNotFoundException(String.format("User not found, or unauthorized %s", username));
        }

        by.n1jel.auction.model.User userFromConfig = userConfig.findByUsername(username);
        return new User(userFromConfig.getUsername(), userFromConfig.getPassword(), new ArrayList<>());
    }

}
