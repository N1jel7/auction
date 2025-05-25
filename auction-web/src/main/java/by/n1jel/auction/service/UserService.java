package by.n1jel.auction.service;

import by.n1jel.auction.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User saveUser(User user);

    User findUserById(Long id);

    User findUserByUsername(String username);
}
